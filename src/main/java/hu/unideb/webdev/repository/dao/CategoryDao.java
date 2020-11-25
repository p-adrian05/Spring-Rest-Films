package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.Model.Category;
import hu.unideb.webdev.exceptions.CategoryAlreadyExistsException;
import hu.unideb.webdev.exceptions.UnknownCategoryException;


import java.util.Collection;

public interface CategoryDao {

    int createCategory(Category category) throws CategoryAlreadyExistsException;

    void updateCategory(Category category) throws UnknownCategoryException, CategoryAlreadyExistsException;

    void deleteCategory(Category category) throws UnknownCategoryException;

    Category getCategoryById(int categoryId) throws UnknownCategoryException;

    Collection<Category> readAll();
}
