package hu.unideb.webdev.service;

import hu.unideb.webdev.Model.Category;
import hu.unideb.webdev.exceptions.CategoryAlreadyExistsException;
import hu.unideb.webdev.exceptions.UnknownCategoryException;
import hu.unideb.webdev.repository.dao.CategoryDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    @Override
    public Collection<Category> getAllCategories() {
        return categoryDao.readAll();
    }

    @Override
    public void recordCategory(Category category) throws CategoryAlreadyExistsException {
        category.setId(0);
        categoryDao.createCategory(category);
    }

    @Override
    public void deleteCategory(Category category) throws UnknownCategoryException {
        categoryDao.deleteCategory(category);
    }

    @Override
    public void updateCategory(Category oldCategory,Category newCategory) throws UnknownCategoryException, CategoryAlreadyExistsException {
        categoryDao.updateCategory(oldCategory,newCategory);
    }

    public Category getCategoryByName(String categoryName) throws UnknownCategoryException {
        return categoryDao.getCategoryByName(categoryName);
    }
}
