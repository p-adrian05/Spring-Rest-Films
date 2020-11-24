package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.repository.util.UnknownCategoryException;
import hu.unideb.webdev.repository.util.UnknownFilmException;

import java.util.Collection;

public interface FilmDao {

    int createFilm(Film film) throws UnknownCategoryException;

    void deleteFilm(Film film) throws UnknownFilmException;

    void updateFilm(Film film) throws UnknownCategoryException, UnknownFilmException;

    Film getFilmById(int filmId) throws UnknownFilmException;

    Collection<Film> readAll();
}
