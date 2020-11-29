package hu.unideb.webdev.service;

import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.exceptions.UnknownActorException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.repository.dao.ActorDao;
import hu.unideb.webdev.repository.util.Rate;
import hu.unideb.webdev.repository.util.SpecialFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
@ExtendWith(MockitoExtension.class)
class ActorServiceImplTest {

    @InjectMocks
    private ActorServiceImpl actorService;
    @Mock
    private ActorDao actorDao;

    @Test
    void testGetAllActors() {
        when(actorDao.readAll()).thenReturn(getActors());
        Collection<Actor> actors = actorService.getAllActors();
        assertThat(getActors(),is(actors));
    }

    @Test
    void getActorsInFilm() {
        when(actorDao.getActorsByFilmId(anyInt())).thenReturn(List.of(getActor()));
        Collection<Actor> actors = actorService.getActorsInFilm(getActor().getFilms().get(0).getId());
        assertTrue(actors.contains(getActor()));
        verify(actorDao,times(1)).getActorsByFilmId(anyInt());
    }
    @Test
    void getActorsInFilmWithUnknownFilm() {
        when(actorDao.getActorsByFilmId(anyInt())).thenReturn(new LinkedList<>());
        Collection<Actor> actors = actorService.getActorsInFilm(1);
        assertEquals(actors.size(), 0);
    }

    @Test
    void testRecordActor() throws UnknownFilmException {
        Actor actor = getActor();

        actorService.recordActor(actor);

        verify(actorDao,times(1)).createActor(actor);
    }
    @Test
    void testRecordActorWithUnknownFilm() throws UnknownFilmException {
        doThrow(UnknownFilmException.class).when(actorDao).createActor(any());
        assertThrows(UnknownFilmException.class,()->
                actorService.recordActor(getActor()));
    }

    @Test
    void deleteActor() throws UnknownActorException {
        Actor actor = getActor();

        actorService.deleteActor(actor.getId());

        verify(actorDao,times(1)).deleteActor(actor.getId());
    }

    @Test
    void testDeleteActorWithUnknownActor() throws UnknownActorException {
        doThrow(UnknownActorException.class).when(actorDao).deleteActor(anyInt());
        assertThrows(UnknownActorException.class,()->
                actorService.deleteActor(getActor().getId()));
    }

    @Test
    void updateActor() throws UnknownActorException, UnknownFilmException {
        Actor actor = getActor();

        actorService.updateActor(actor);

        verify(actorDao,times(1)).updateActor(actor);
    }

    @Test
    void testUpdateActorWithUnknownActor() throws UnknownActorException, UnknownFilmException {
        doThrow(UnknownActorException.class).when(actorDao).updateActor(any());
        assertThrows(UnknownActorException.class,()->
                actorService.updateActor(getActor()));
    }
    @Test
    void testUpdateActorWithUnknownFilm() throws UnknownActorException, UnknownFilmException {
        doThrow(UnknownFilmException.class).when(actorDao).updateActor(any());
        assertThrows(UnknownFilmException.class,()->
                actorService.updateActor(getActor()));
    }

    @Test
    void getActorById() throws UnknownActorException {
        when(actorDao.getActorById(anyInt())).thenReturn(getActor());
        Actor actor = actorService.getActorById(0);
        assertThat(getActor(),is(actor));
    }
    @Test
    void getActorByIdWithUnknownActor() throws UnknownActorException {
        doThrow(UnknownActorException.class).when(actorDao).getActorById(anyInt());
        assertThrows(UnknownActorException.class,()->
                actorService.getActorById(0));
    }

    @Test
    void testGetActorsByName(){
        String name = "name";
        when(actorDao.getActorsByName(name)).thenReturn(List.of(getActor()));
        Collection<Actor> actors = actorService.getActorsByName(name);
        assertTrue(actors.contains(getActor()));
    }
    @Test
    void testGetActorsByFilmId(){
        when(actorDao.getActorsByFilmId(1)).thenReturn(List.of(getActor()));
        Collection<Actor> actors = actorService.getActorsByFilmId(1);
        assertTrue(actors.contains(getActor()));
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
                                .build()))
                .build();
    }
    public Collection<Actor> getActors(){
        return List.of(Actor.builder()
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
                                .build()))
                .build(),
                Actor.builder()
                        .id(0)
                        .firstName("Test Actor FirstName")
                        .lastName("Test Actor LastName")
                        .films(new LinkedList<>()).build()
        );
    }
}