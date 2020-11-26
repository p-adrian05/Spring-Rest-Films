package hu.unideb.webdev.service;

import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.exceptions.UnknownActorException;
import hu.unideb.webdev.exceptions.UnknownFilmException;

import java.util.Collection;

public interface ActorService {


    Collection<Actor> getAllActors();

    Collection<Actor> getActorsInFilm(Film film);

    void recordActor(Actor actor) throws UnknownFilmException;

    void deleteActor(Actor actor) throws UnknownActorException;

    void updateActor(Actor actor) throws UnknownActorException, UnknownFilmException;

    Actor getActorById(int actorId) throws UnknownActorException;

}
