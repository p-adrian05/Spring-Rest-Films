package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.exceptions.UnknownCategoryException;
import hu.unideb.webdev.exceptions.UnknownFilmException;

import java.util.Collection;

public interface FilmDao {

    int createFilm(Film film) throws UnknownCategoryException;

    void deleteFilm(int filmId) throws UnknownFilmException;

    void updateFilm(Film film) throws UnknownCategoryException, UnknownFilmException;

    Film getFilmById(int filmId) throws UnknownFilmException;
     Collection<Film> getFilmsByName(String name);
    Collection<Film> readAll();
}
