package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.exceptions.UnknownCategoryException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.repository.*;
import hu.unideb.webdev.repository.entity.CategoryEntity;
import hu.unideb.webdev.repository.entity.FilmCategoryEntity;
import hu.unideb.webdev.repository.entity.FilmEntity;
import hu.unideb.webdev.repository.entity.LanguageEntity;
import hu.unideb.webdev.repository.util.FilmCategoryId;
import hu.unideb.webdev.repository.util.Rate;
import hu.unideb.webdev.repository.util.SpecialFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FilmDaoImplTest {

    @Spy
    @InjectMocks
    private FilmDaoImpl filmDao;

    @Mock
    private FilmRepository filmRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private FilmCategoryRepository filmCategoryRepository;
    @Mock
    private FilmActorRepository filmActorRepository;

    @Test
    void testCreateFilm() throws UnknownCategoryException {

        doReturn(getCategory())
                .when(filmDao).queryCategory(anyString());
        doReturn(getFilmEntity())
                .when(filmDao).convertFilmToFilmEntity(any());

        int savedId = filmDao.createFilm(getFilm());
        verify(filmDao,times(1)).convertFilmToFilmEntity(any());
        verify(filmRepository,times(1)).save(any());
        verify(filmCategoryRepository,times(2)).save(any());
        assertEquals(savedId,1);
    }

    @Test
    void testDeleteFilm() throws UnknownFilmException {
        when(filmRepository.findById(anyInt())).thenReturn(Optional.ofNullable(getFilmEntity()));

        when(filmCategoryRepository.findByFilm(any())).thenReturn(List.of(getFilmCategory(),getFilmCategory()));
        filmDao.deleteFilm(getFilm());

        verify(filmActorRepository,times(1)).findByFilm(any());
        verify(filmCategoryRepository,times(2)).delete(any());
        verify(filmActorRepository,times(0)).delete(any());
        verify(filmRepository,times(1)).delete(any());
    }

    @Test
    void testDeleteFilmWithUnknownFilm(){
        when(filmRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(UnknownFilmException.class,()->filmDao.deleteFilm(getFilm()));
    }

    @Test
    void testUpdateFilm() throws UnknownFilmException, UnknownCategoryException {
        doReturn(getFilmEntity())
                .when(filmDao).convertFilmToFilmEntity(any());
        doReturn(getFilm())
                .when(filmDao).getFilmById(anyInt());
        doReturn(getCategory())
                .when(filmDao).queryCategory(anyString());
        Film film = getFilm();
        film.setCategories(List.of("Action","New"));
        filmDao.updateFilm(film);

        verify(filmDao,times(1)).convertFilmToFilmEntity(any());
        verify(filmCategoryRepository,times(1)).save(any());
        verify(filmCategoryRepository,times(1)).delete(any());
        verify(filmRepository,times(1)).save(any());
    }

    @Test
    void testReadAll() {
        filmDao.readAll();
        verify(filmRepository,times(1)).findAll();

    }

    @Test
    void testGetFilmById() throws UnknownFilmException {
        when(filmRepository.findById(anyInt())).thenReturn(Optional.ofNullable(getFilmEntity()));
        filmDao.getFilmById(1);
        verify(filmDao,times(1)).convertFilmEntityToFilm(any());
    }
    @Test
    void testGetFilmByIdWithUnknownFilm(){
        when(filmRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(UnknownFilmException.class,()-> filmDao.getFilmById(anyInt()));
    }

    @Test
    void testQueryCategory() throws UnknownCategoryException {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.ofNullable(getCategory()));
        filmDao.queryCategory(getCategory().getName());
        verify(categoryRepository,times(1)).findByName(anyString());
    }
    @Test
    void testQueryCategoryWithUnknownCategory() {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        assertThrows(UnknownCategoryException.class,()->filmDao.queryCategory(anyString()));
    }
    @Test
    void testQueryLanguage() {
        when(languageRepository.findByName(anyString()))
                .thenReturn(Optional.empty());
        filmDao.queryLanguage("hungarian");
        verify(languageRepository,times(1)).save(any());
    }

    private Film getFilm(){
       return Film.builder().description("Description")
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
       .categories(List.of("Action","Animal"))
       .build();
    }

    private FilmEntity getFilmEntity(){
        return FilmEntity.builder()
                .id(1)
                .title("Test Film")
                .description("Description")
                .releaseYear(2006)
                .languageEntity(LanguageEntity.builder().name("hungarian")
                        .id(1).lastUpdate(new Timestamp((new Date()).getTime())).build())
                .originalLanguageEntity(LanguageEntity.builder().name("english")
                        .id(2).lastUpdate(new Timestamp((new Date()).getTime())).build())
                .rentalDuration(13)
                .rentalRate(23.4)
                .length(23)
                .replacementCost(23.3)
                .rating(Rate.NC17)
                .specialFeatures(List.of(SpecialFeature.DELETED_SCENES,SpecialFeature.COMMENTARIES))
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
    }


    private CategoryEntity getCategory(){
        return CategoryEntity.builder()
                .id(1)
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .name("Action").build();
    }
    private FilmCategoryEntity getFilmCategory(){
        return FilmCategoryEntity.builder().category(new CategoryEntity()).film(new FilmEntity()).id(new FilmCategoryId())
                .lastUpdate(new Timestamp((new Date()).getTime())).build();
    }



}