package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.exceptions.UnknownActorException;
import hu.unideb.webdev.exceptions.UnknownFilmException;

import java.util.Collection;
import java.util.List;

public interface ActorDao {

    int createActor(Actor actor) throws UnknownFilmException;

    void deleteActor(int actorId) throws UnknownActorException;

    void updateActor(Actor actor) throws UnknownActorException, UnknownFilmException;

    Actor getActorById(int actorId) throws UnknownActorException;

    public List<Actor> getActorsByName(String name) throws UnknownActorException;

    Collection<Actor> readAll();

    Collection<Actor> getActorsByFilmId(int filmId);
}
