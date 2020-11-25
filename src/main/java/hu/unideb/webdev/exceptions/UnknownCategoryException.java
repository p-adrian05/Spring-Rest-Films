package hu.unideb.webdev.exceptions;

public class UnknownCategoryException extends Exception{

    public UnknownCategoryException() {
    }

    public UnknownCategoryException(String message) {
        super(message);
    }

    public UnknownCategoryException(String message, String categoryName) {
    }
}
