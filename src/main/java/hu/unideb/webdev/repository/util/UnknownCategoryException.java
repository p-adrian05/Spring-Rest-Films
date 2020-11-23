package hu.unideb.webdev.repository.util;

public class UnknownCategoryException extends Exception{

    public UnknownCategoryException() {
    }

    public UnknownCategoryException(String message) {
        super(message);
    }
}
