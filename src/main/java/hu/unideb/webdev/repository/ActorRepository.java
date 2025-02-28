package hu.unideb.webdev.repository;

import hu.unideb.webdev.repository.entity.ActorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ActorRepository extends CrudRepository<ActorEntity,Integer> {

    @Query("select a from ActorEntity a join FilmActorEntity f on a.id = f.id.actorId where f.id.filmId = ?1")
    List<ActorEntity> findByFilmId(int filmId);

    List<ActorEntity> findByFirstNameOrLastName(String firstName, String lastName);
    List<ActorEntity> findByFirstNameAndLastName(String firstName, String lastName);
}
