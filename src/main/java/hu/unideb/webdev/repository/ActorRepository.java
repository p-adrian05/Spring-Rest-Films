package hu.unideb.webdev.repository;

import hu.unideb.webdev.repository.entity.ActorEntity;
import hu.unideb.webdev.repository.entity.FilmActorEntity;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<ActorEntity,Integer> {


}
