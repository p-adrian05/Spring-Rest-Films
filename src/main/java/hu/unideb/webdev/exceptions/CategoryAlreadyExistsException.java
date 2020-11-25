package hu.unideb.webdev.exceptions;

public class CategoryAlreadyExistsException extends Exception{

    public CategoryAlreadyExistsException() {
    }

    public CategoryAlreadyExistsException(String message) {
        super(message);
    }

    public CategoryAlreadyExistsException(String message, String categoryName) {
    }
}
