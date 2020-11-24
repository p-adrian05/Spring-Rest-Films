package hu.unideb.webdev.repository;

import hu.unideb.webdev.repository.entity.ActorEntity;
import hu.unideb.webdev.repository.entity.FilmEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface FilmRepository extends CrudRepository<FilmEntity,Integer> {

    @Query("select f from FilmEntity f join FilmActorEntity fa on f.id = fa.id.filmId where fa.id.actorId = ?1")
    List<FilmEntity> findByActorId(int actorId);
}
