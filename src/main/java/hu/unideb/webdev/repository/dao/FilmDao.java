package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Film;

import java.util.Collection;

public interface FilmDao {

    void createFilm(Film film);

    void deleteFilm(Film film);

    void updateFilm(Film film);

    Collection<Film> readAll();
}
