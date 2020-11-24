package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.repository.util.UnknownCategoryException;
import hu.unideb.webdev.repository.util.UnknownActorException;

import java.util.Collection;

public interface ActorDao {

    int createActor(Actor actor);

    void deleteActor(Actor actor) throws UnknownActorException;

    void updateActor(Actor actor) throws UnknownActorException;

    Actor getActorById(int actorId) throws UnknownActorException;

    Collection<Actor> readAll();
}
