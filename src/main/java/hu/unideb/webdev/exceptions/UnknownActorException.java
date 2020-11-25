package hu.unideb.webdev.exceptions;

import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.Model.Film;

public class UnknownActorException extends Exception{


    private Actor actor;

    public UnknownActorException() {
    }

    public UnknownActorException(String message) {
        super(message);
    }
    public UnknownActorException(Actor actor) {
        this.actor = actor;
    }

    public UnknownActorException(String message, Actor actor){
        super(message);
        this.actor = actor;
    }

}
