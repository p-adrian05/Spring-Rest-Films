package hu.unideb.webdev.repository;

import hu.unideb.webdev.repository.entity.FilmEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface FilmRepository extends CrudRepository<FilmEntity,Integer> {
}
