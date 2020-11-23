package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.repository.util.UnknownCategoryException;

import java.util.Collection;

public interface FilmDao {

    void createFilm(Film film) throws UnknownCategoryException;

    void deleteFilm(Film film);

    void updateFilm(Film film);

    Collection<Film> readAll();
}
