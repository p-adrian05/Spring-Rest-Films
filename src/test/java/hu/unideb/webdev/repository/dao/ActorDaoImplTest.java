package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.exceptions.UnknownActorException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.repository.ActorRepository;
import hu.unideb.webdev.repository.FilmActorRepository;
import hu.unideb.webdev.repository.FilmRepository;
import hu.unideb.webdev.repository.entity.*;
import hu.unideb.webdev.repository.util.Rate;
import hu.unideb.webdev.repository.util.SpecialFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorDaoImplTest {

    @Spy
    @InjectMocks
    private ActorDaoImpl actorDao;

    @Mock
    private FilmActorRepository filmActorRepository;
    @Mock
    private ActorRepository actorRepository;
    @Mock
    private FilmRepository filmRepository;


    @Test
    void createActor() throws UnknownFilmException {
        doReturn(new FilmEntity())
                .when(actorDao).queryFilm(any());
        actorDao.createActor(getActor());
        verify(actorRepository,times(1)).save(any());
        verify(filmActorRepository,times(2)).save(any());
    }
    @Test
    void createActorWithNoFilms() throws UnknownFilmException {
        Actor actor = getActor();
        actor.setFilms(new LinkedList<>());
        doReturn(getActorEntity()).when(actorDao)
                .convertActorToActorEntity(any());
        int savedId = actorDao.createActor(actor);
        verify(actorRepository,times(1)).save(any());
        verify(filmActorRepository,times(0)).save(any());
        assertEquals(savedId,1);
    }

    @Test
    void deleteActor() throws UnknownActorException {
        doReturn(Optional.of(getActorEntity())).when(actorRepository)
                .findById(anyInt());
        when(filmActorRepository.findByActor(any()))
                .thenReturn(List.of(new FilmActorEntity(),new FilmActorEntity()));

        actorDao.deleteActor(getActor().getId());
        verify(filmActorRepository,times(1)).findByActor(any());
        verify(actorRepository,times(1)).findById(any());
        verify(filmActorRepository,times(2)).delete(any());
        verify(actorRepository,times(1)).delete(any());
    }
    @Test
    void deleteActorWithUnknownActor(){
        doReturn(Optional.empty()).when(actorRepository)
                .findById(anyInt());

        assertThrows(UnknownActorException.class,()->actorDao.deleteActor(getActor().getId()));
        verify(actorRepository,times(1)).findById(any());
    }

    @Test
    void updateActor() throws UnknownActorException, UnknownFilmException {
        Actor actorNew = getActor();
        actorNew.setFilms(List.of(Film.builder().description("Description")
                        .id(3)
                        .language("hungarian")
                        .originalLanguage("english")
                        .length(23)
                        .rating(Rate.NC17)
                        .releaseYear(2006)
                        .rentalDuration(13)
                        .rentalRate(23.4)
                        .replacementCost(23.3)
                        .title("Test Film3")
                        .specialFeatures(List.of(SpecialFeature.COMMENTARIES))
                        .build()));
        doReturn(true).when(actorRepository)
                .existsById(anyInt());
        doReturn(new FilmEntity()).when(actorDao)
                .queryFilm(any());
        doReturn(List.of(new FilmEntity(),new FilmEntity())).when(filmRepository)
                .findByActorId(anyInt());
        actorDao.updateActor(actorNew);
        verify(filmActorRepository,times(1)).save(any());
        verify(filmRepository,times(1)).findByActorId(anyInt());
        verify(filmActorRepository,times(2)).delete(any());
        verify(actorRepository,times(1)).save(any());
    }

    @Test
    void testGetActorsByNameWithOneName(){
        String name = "firstName";
        doReturn(List.of(getActorEntity())).when(actorRepository).findByFirstNameOrLastName(anyString(),anyString());
        actorDao.getActorsByName(name);
        verify(actorRepository,times(1)).findByFirstNameOrLastName(name,name);
        verify(actorRepository,times(0)).findByFirstNameAndLastName(anyString(),anyString());
        verify(actorDao,times(1)).queryFilms(anyInt());
    }

    @Test
    void testGetActorsByNameWithTwoNames() {
        String name = "firstName lastname";
        doReturn(List.of(getActorEntity())).when(actorRepository).findByFirstNameAndLastName(anyString(),anyString());
        actorDao.getActorsByName(name);
        verify(actorRepository,times(0)).findByFirstNameOrLastName(name,name);
        verify(actorRepository,times(1)).findByFirstNameAndLastName(anyString(),anyString());
        verify(actorDao,times(1)).queryFilms(anyInt());
    }


    @Test
    void getActorById() throws UnknownActorException {
        doReturn(Optional.of(getActorEntity()))
                .when(actorRepository).findById(any());
        actorDao.getActorById(1);
        verify(actorDao,times(1)).convertActorEntityToActor(any());
    }
    @Test
    void getActorByIdWithUnknownActor(){
        doReturn(Optional.empty())
                .when(actorRepository).findById(any());
        assertThrows(UnknownActorException.class,()->actorDao.getActorById(2));
        verify(actorDao,times(0)).convertActorEntityToActor(any());
    }

    @Test
    void readAll() {
        actorDao.readAll();
        verify(actorRepository,times(1)).findAll();
    }

    @Test
    void queryFilms() {
        FilmEntity filmEntity = new FilmEntity();
        filmEntity.setLanguageEntity(new LanguageEntity());
        doReturn(List.of(filmEntity))
                .when(filmRepository).findByActorId(anyInt());
        actorDao.queryFilms(1);
        verify(filmRepository,times(1)).findByActorId(anyInt());
    }

    @Test
    void queryFilm() throws UnknownFilmException {
        doReturn(Optional.of(new FilmEntity()))
                .when(filmRepository).findById(anyInt());
        actorDao.queryFilm(getActor().getFilms().get(0));
        verify(filmRepository,times(1)).findById(anyInt());
    }

    @Test
    void testGetActorsByFilmId(){
        actorDao.getActorsByFilmId(1);
        verify(actorRepository,times(1)).findByFilmId(anyInt());
    }

    @Test
    void queryFilmWithUnknownFilm(){
        doReturn(Optional.empty())
                .when(filmRepository).findById(anyInt());
        assertThrows(UnknownFilmException.class,()->actorDao.queryFilm(new Film()));
        verify(filmRepository,times(1)).findById(anyInt());
    }

    private Actor getActor(){
        return Actor.builder()
                .id(0)
                .firstName("Test Actor FirstName")
                .lastName("Test Actor LastName")
                .films(List.of(Film.builder().description("Description")
                        .id(1)
                        .language("hungarian")
                        .originalLanguage("english")
                        .length(23)
                        .rating(Rate.NC17)
                        .releaseYear(2006)
                        .rentalDuration(13)
                        .rentalRate(23.4)
                        .replacementCost(23.3)
                        .title("Test Film")
                        .specialFeatures(List.of(SpecialFeature.DELETED_SCENES,SpecialFeature.COMMENTARIES))
                        .build(),
                        Film.builder().description("Description")
                                .id(2)
                                .language("hungarian")
                                .originalLanguage("english")
                                .length(23)
                                .rating(Rate.NC17)
                                .releaseYear(2006)
                                .rentalDuration(13)
                                .rentalRate(23.4)
                                .replacementCost(23.3)
                                .title("Test Film2")
                                .specialFeatures(List.of(SpecialFeature.COMMENTARIES))
                                .build()))
                .build();
    }

    private ActorEntity getActorEntity(){
        return ActorEntity.builder()
                .id(1)
                .firstName("Test Actor FirstName")
                .lastName("Test Actor LastName")
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
    }


}