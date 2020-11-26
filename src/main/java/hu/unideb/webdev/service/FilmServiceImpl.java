package hu.unideb.webdev.service;

import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.exceptions.UnknownCategoryException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.repository.dao.FilmDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {

    private final FilmDao filmDao;

    @Override
    public Collection<Film> getAllFilm() {
        return filmDao.readAll();
    }

    @Override
    public Collection<Film> getFilmsInCategory(String name) {
        return filmDao.readAll().parallelStream()
                .filter(film -> film.getCategories().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public void recordFilm(Film film) throws UnknownCategoryException {
        film.setId(0);
        filmDao.createFilm(film);
    }

    @Override
    public void deleteFilm(Film film) throws UnknownFilmException {
        filmDao.deleteFilm(film);
    }

    @Override
    public void updateFilm(Film film) throws UnknownFilmException, UnknownCategoryException {
        filmDao.updateFilm(film);
    }

    @Override
    public Film getFilmById(int filmId) throws UnknownFilmException {
        return filmDao.getFilmById(filmId);
    }
    @Override
    public Collection<Film> getFilmsByName(String name) {
        return filmDao.getFilmsByName(name);
    }
}
