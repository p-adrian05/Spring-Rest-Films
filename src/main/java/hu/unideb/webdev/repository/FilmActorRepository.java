package hu.unideb.webdev.repository;

import hu.unideb.webdev.repository.entity.ActorEntity;
import hu.unideb.webdev.repository.entity.FilmActorEntity;
import hu.unideb.webdev.repository.entity.FilmEntity;
import hu.unideb.webdev.repository.util.FilmActorId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface FilmActorRepository extends CrudRepository<FilmActorEntity, FilmActorId> {

    List<FilmActorEntity> findByFilm(FilmEntity filmEntity);
    FilmActorEntity findByFilmAndActor(FilmEntity filmEntity, ActorEntity actorEntity);
}
