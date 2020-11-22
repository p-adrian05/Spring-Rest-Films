package hu.unideb.webdev.repository;

import hu.unideb.webdev.repository.entity.*;
import hu.unideb.webdev.repository.util.FilmCategoryId;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface FilmCategoryRepository extends CrudRepository<FilmCategoryEntity, FilmCategoryId> {

    Set<FilmCategoryEntity> findByFilm(FilmEntity filmEntity);
    FilmCategoryEntity findByFilmAndCategory(FilmEntity filmEntity, CategoryEntity categoryEntity);
}
