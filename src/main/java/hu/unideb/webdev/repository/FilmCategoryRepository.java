package hu.unideb.webdev.repository;

import hu.unideb.webdev.repository.entity.*;
import hu.unideb.webdev.repository.util.FilmCategoryId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface FilmCategoryRepository extends CrudRepository<FilmCategoryEntity, FilmCategoryId> {

    List<FilmCategoryEntity> findByFilm(FilmEntity filmEntity);
    List<FilmCategoryEntity> findByCategory(CategoryEntity categoryEntity);
    FilmCategoryEntity findByFilmAndCategory(FilmEntity filmEntity, CategoryEntity categoryEntity);
    Integer countFilmCategoryEntityByCategory(CategoryEntity categoryEntity);
}
