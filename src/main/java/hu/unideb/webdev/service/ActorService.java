package hu.unideb.webdev.service;

import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.exceptions.UnknownActorException;
import hu.unideb.webdev.exceptions.UnknownFilmException;

import java.util.Collection;
import java.util.List;

public interface ActorService {


    Collection<Actor> getAllActors();

    Collection<Actor> getActorsInFilm(int filmId);

    void recordActor(Actor actor) throws UnknownFilmException;

    void deleteActor(int actorId) throws UnknownActorException;

    void updateActor(Actor actor) throws UnknownActorException, UnknownFilmException;

    Actor getActorById(int actorId) throws UnknownActorException;

    List<Actor> getActorsByName(String name);

    Collection<Actor> getActorsByFilmId(int filmId);
}
