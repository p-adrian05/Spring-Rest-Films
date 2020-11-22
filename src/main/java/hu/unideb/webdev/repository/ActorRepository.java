package hu.unideb.webdev.repository;

import hu.unideb.webdev.repository.entity.ActorEntity;
import hu.unideb.webdev.repository.entity.FilmActorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface ActorRepository extends CrudRepository<ActorEntity,Integer> {

    @Query("select a from ActorEntity a join FilmActorEntity f on a.id = f.id.actorId where f.id.filmId = ?1")
    List<ActorEntity> findByFilm(int filmId);
}
