package hu.unideb.webdev.service;

import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.exceptions.UnknownCategoryException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.repository.dao.FilmDao;
import hu.unideb.webdev.repository.util.Rate;
import hu.unideb.webdev.repository.util.SpecialFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceImplTest {

    @InjectMocks
    private FilmServiceImpl filmService;
    @Mock
    private FilmDao filmDao;


    @Test
    void getAllFilm() {
        when(filmDao.readAll()).thenReturn(getFilms());
        Collection<Film> films = filmService.getAllFilm();
        assertThat(getFilms(),is(films));
    }

    @Test
    void getFilmsInCategory() {
        when(filmDao.readAll()).thenReturn(getFilms());
        Collection<Film> films = filmService.getFilmsInCategory(getFilm().getCategories().get(0));
        assertTrue(films.contains(getFilm()));
    }

    @Test
    void recordFilm() throws UnknownCategoryException {
        Film film = getFilm();

        filmService.recordFilm(film);
        verify(filmDao,times(1)).createFilm(film);
    }
    @Test
    void recordFilmWithUnknownCategory() throws UnknownCategoryException {
        doThrow(UnknownCategoryException.class).when(filmDao).createFilm(any());
        assertThrows(UnknownCategoryException.class,()->
                filmService.recordFilm(getFilm()));
    }

    @Test
    void deleteFilm() throws UnknownFilmException {
        Film film = getFilm();

        filmService.deleteFilm(film.getId());
        verify(filmDao,times(1)).deleteFilm(film.getId());
    }
    @Test
    void deleteFilmWithUnknownFilm() throws UnknownFilmException {
        doThrow(UnknownFilmException.class).when(filmDao).deleteFilm(anyInt());
        assertThrows(UnknownFilmException.class,()->
                filmService.deleteFilm(getFilm().getId()));
    }
    @Test
    void updateFilm() throws UnknownFilmException, UnknownCategoryException {
        Film film = getFilm();

        filmService.updateFilm(film);
        verify(filmDao,times(1)).updateFilm(film);
    }
    @Test
    void updateFilmWithUnknownCategory() throws UnknownCategoryException, UnknownFilmException {
        doThrow(UnknownCategoryException.class).when(filmDao).updateFilm(any());
        assertThrows(UnknownCategoryException.class,()->
                filmService.updateFilm(getFilm()));
    }
    @Test
    void updateFilmWithUnknownFilm() throws UnknownFilmException, UnknownCategoryException {
        doThrow(UnknownFilmException.class).when(filmDao).updateFilm(any());
        assertThrows(UnknownFilmException.class,()->
                filmService.updateFilm(getFilm()));
    }
    @Test
    void getFilmById() throws UnknownFilmException {
        when(filmDao.getFilmById(anyInt())).thenReturn(getFilm());
        Film film = filmService.getFilmById(0);
        assertThat(getFilm(),is(film));
    }
    @Test
    void getFilmByIdWithUnknownFilm() throws UnknownFilmException {
        doThrow(UnknownFilmException.class).when(filmDao).getFilmById(anyInt());
        assertThrows(UnknownFilmException.class,()->
                filmService.getFilmById(0));
    }

    @Test
    void testGetFilmsByTitle(){
        String title = "title";
        when(filmDao.getFilmsByTitle(title)).thenReturn(List.of(getFilm()));
        Collection<Film> films = filmService.getFilmsByTitle(title);
        assertTrue(films.contains(getFilm()));
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
                .specialFeatures(List.of(SpecialFeature.BEHIND_THE_SCENES,SpecialFeature.COMMENTARIES))
                .categories(List.of("Action","Animal"))
                .build();
    }
    private Collection<Film> getFilms(){
        return List.of(Film.builder().description("Description")
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
                .specialFeatures(List.of(SpecialFeature.BEHIND_THE_SCENES,SpecialFeature.COMMENTARIES))
                .categories(List.of("Action","Animal"))
                .build(),
                Film.builder().description("Description")
                        .id(1)
                        .language("hungarian")
                        .originalLanguage("english")
                        .length(23)
                        .rating(Rate.NC17)
                        .releaseYear(2006)
                        .rentalDuration(13)
                        .rentalRate(23.4)
                        .replacementCost(23.3)
                        .title("Test Film2")
                        .specialFeatures(List.of(SpecialFeature.DELETED_SCENES,SpecialFeature.TRAILERS))
                        .categories(List.of("Animal"))
                        .build());
    }

}