package hu.unideb.webdev.exceptions;

import hu.unideb.webdev.Model.Film;

public class UnknownFilmException extends Exception{


    private Film film;

    public UnknownFilmException() {
    }

    public UnknownFilmException(String message) {
        super(message);
    }
    public UnknownFilmException(Film film) {
        this.film = film;
    }

    public UnknownFilmException(String message,Film film){
        super(message);
        this.film = film;
    }

}
