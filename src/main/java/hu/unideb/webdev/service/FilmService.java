package hu.unideb.webdev.service;

import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.exceptions.UnknownCategoryException;
import hu.unideb.webdev.exceptions.UnknownFilmException;

import java.util.Collection;

public interface FilmService {

    Collection<Film> getAllFilm();

    Collection<Film> getFilmsInCategory(String name);

    void recordFilm(Film film) throws UnknownCategoryException;

    void deleteFilm(int filmId) throws UnknownFilmException;

    void updateFilm(Film film) throws UnknownFilmException, UnknownCategoryException;

    Film getFilmById(int filmId) throws UnknownFilmException;

    Collection<Film> getFilmsByName(String name);
}
