package hu.unideb.webdev.repository.dao;


import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.Model.Category;
import hu.unideb.webdev.exceptions.CategoryAlreadyExistsException;
import hu.unideb.webdev.exceptions.UnknownCategoryException;
import hu.unideb.webdev.repository.CategoryRepository;
import hu.unideb.webdev.repository.FilmCategoryRepository;
import hu.unideb.webdev.repository.entity.ActorEntity;
import hu.unideb.webdev.repository.entity.CategoryEntity;
import hu.unideb.webdev.repository.entity.FilmCategoryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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
        Optional<CategoryEntity> categoryEntityExisted = categoryRepository.findByName(category.getName());
        if(categoryEntityExisted.isPresent()){
            throw new CategoryAlreadyExistsException(category.getName());
        }
        CategoryEntity categoryEntity = convertCategoryToCategoryEntity(category);
        log.info("Created category: {}",categoryEntity);
        try{
            categoryRepository.save(categoryEntity);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return categoryEntity.getId();
    }

    @Override
    public void updateCategory(Category category) throws UnknownCategoryException, CategoryAlreadyExistsException {
        if(!categoryRepository.existsById(category.getId())){
            throw new UnknownCategoryException(String.format("Category not found by id: %s",category.getId()));
        }
        if(categoryRepository.findByName(category.getName()).isPresent()){
            throw new CategoryAlreadyExistsException(category.getName());
        }
        CategoryEntity categoryEntity = convertCategoryToCategoryEntity(category);
        log.info("Updated category : {}",categoryEntity);
        try{
            categoryRepository.save(categoryEntity);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    public void deleteCategory(Category category) throws UnknownCategoryException {
        if(!categoryRepository.existsById(category.getId()) ||
            categoryRepository.findByName(category.getName()).isEmpty()){
            throw new UnknownCategoryException(String.format("Category not found %s",category));
        }
        CategoryEntity categoryEntity = convertCategoryToCategoryEntity(category);
        log.info("Deleted category: {}",categoryEntity);
        List<FilmCategoryEntity> filmCategoryEntities = filmCategoryRepository.findByCategory(categoryEntity);
        log.info("Deleted category-film connections: {}",filmCategoryEntities.size());
        try{
            filmCategoryEntities.forEach(filmCategoryRepository::delete);
            categoryRepository.delete(categoryEntity);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    public Category getCategoryById(int categoryId) throws UnknownCategoryException {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);
        if(categoryEntity.isEmpty()){
            throw new UnknownCategoryException(String.format("Category not found by id: %s",categoryId));
        }
        log.info("Category entity by id {}, {}",categoryId,categoryEntity.get());
        return convertCategoryEntityToCategory(categoryEntity.get());
    }

    @Override
    public Collection<Category> readAll() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(),true)
                .map(this::convertCategoryEntityToCategory)
                .collect(Collectors.toList());
    }

    protected Category convertCategoryEntityToCategory(CategoryEntity categoryEntity){
        return Category.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .build();
    }
    protected CategoryEntity convertCategoryToCategoryEntity(Category category){
        return CategoryEntity.builder()
                .id(category.getId())
                .name(category.getName())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
    }

}
