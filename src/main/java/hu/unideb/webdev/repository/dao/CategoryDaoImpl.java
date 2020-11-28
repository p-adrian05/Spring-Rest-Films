package hu.unideb.webdev.repository.dao;


import hu.unideb.webdev.Model.Category;
import hu.unideb.webdev.exceptions.CategoryAlreadyExistsException;
import hu.unideb.webdev.exceptions.UnknownCategoryException;
import hu.unideb.webdev.repository.CategoryRepository;
import hu.unideb.webdev.repository.FilmCategoryRepository;
import hu.unideb.webdev.repository.entity.CategoryEntity;
import hu.unideb.webdev.repository.entity.FilmCategoryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CategoryDaoImpl implements CategoryDao {

    private final CategoryRepository categoryRepository;
    private final FilmCategoryRepository filmCategoryRepository;


    @Override
    public int createCategory(Category category) throws CategoryAlreadyExistsException {
        if (categoryRepository.existsCategoryEntityByName(category.getName())) {
            throw new CategoryAlreadyExistsException(String.format("Category already exists: %s", category.getName()));
        }
        CategoryEntity categoryEntity = convertCategoryToCategoryEntity(category);
        log.info("Created category: {}", categoryEntity);
        try {
            categoryRepository.save(categoryEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return categoryEntity.getId();
    }

    @Override
    public void updateCategory(Category oldCategory,Category newCategory) throws UnknownCategoryException, CategoryAlreadyExistsException {
        Optional<CategoryEntity> oldCategoryEntity = categoryRepository.findByName(oldCategory.getName());
        if (oldCategoryEntity.isEmpty()) {
            throw new UnknownCategoryException(String.format("Category not found : %s", oldCategory.getName()));
        }
        if (categoryRepository.existsCategoryEntityByName(newCategory.getName())) {
            throw new CategoryAlreadyExistsException(String.format("Category already exists: %s",newCategory));
        }
        CategoryEntity entity = convertCategoryToCategoryEntity(newCategory);
        entity.setId(oldCategoryEntity.get().getId());
        log.info("Updated category : {}", entity);
        try {
            categoryRepository.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteCategory(String name) throws UnknownCategoryException {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(name);
        if (categoryEntity.isEmpty()) {
            throw new UnknownCategoryException(String.format("Category not found %s", name));
        }
        log.info("Deleted category: {}", categoryEntity.get());
        List<FilmCategoryEntity> filmCategoryEntities = filmCategoryRepository.findByCategory(categoryEntity.get());
        log.info("Deleted category-film connections: {}", filmCategoryEntities.size());
        try {
            filmCategoryEntities.forEach(filmCategoryRepository::delete);
            categoryRepository.delete(categoryEntity.get());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Category getCategoryByName(String categoryName) throws UnknownCategoryException {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(categoryName);
        if (categoryEntity.isEmpty()) {
            throw new UnknownCategoryException(String.format("Category not found by name: %s", categoryName));
        }
        log.info("Category entity by name {}, {}", categoryName, categoryEntity.get());
        return convertCategoryEntityToCategory(categoryEntity.get());
    }

    @Override
    public Collection<Category> readAll() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), true)
                .map(this::convertCategoryEntityToCategory)
                .collect(Collectors.toList());
    }

    protected Category convertCategoryEntityToCategory(CategoryEntity categoryEntity) {
        return Category.builder()
                .name(categoryEntity.getName())
                .filmCount(filmCategoryRepository.countFilmCategoryEntityByCategory(categoryEntity))
                .build();
    }

    protected CategoryEntity convertCategoryToCategoryEntity(Category category) {
        return CategoryEntity.builder()
                .id(0)
                .name(category.getName())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
    }

}
