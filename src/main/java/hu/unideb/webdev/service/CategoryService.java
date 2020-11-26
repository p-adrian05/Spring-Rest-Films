package hu.unideb.webdev.service;

import hu.unideb.webdev.Model.Category;
import hu.unideb.webdev.exceptions.CategoryAlreadyExistsException;
import hu.unideb.webdev.exceptions.UnknownCategoryException;

import java.util.Collection;

public interface CategoryService {

    Collection<Category> getAllCategories();

    void recordCategory(Category category) throws CategoryAlreadyExistsException;

    void deleteCategory(Category category) throws UnknownCategoryException;

    void updateCategory(Category category) throws UnknownCategoryException, CategoryAlreadyExistsException;

    Category getCategoryById(int categoryId) throws UnknownCategoryException;

}
