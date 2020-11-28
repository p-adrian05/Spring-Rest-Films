package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Category;
import hu.unideb.webdev.exceptions.CategoryAlreadyExistsException;
import hu.unideb.webdev.exceptions.UnknownCategoryException;

import java.util.Collection;

public interface CategoryDao {

    int createCategory(Category category) throws CategoryAlreadyExistsException;

    void updateCategory(Category oldCategory,Category newCategory) throws UnknownCategoryException, CategoryAlreadyExistsException;

    void deleteCategory(String name) throws UnknownCategoryException;

    Category getCategoryByName(String categoryName) throws UnknownCategoryException;

    Collection<Category> readAll();
}
