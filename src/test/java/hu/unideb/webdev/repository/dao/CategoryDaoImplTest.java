package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Category;
import hu.unideb.webdev.exceptions.CategoryAlreadyExistsException;
import hu.unideb.webdev.exceptions.UnknownCategoryException;
import hu.unideb.webdev.repository.CategoryRepository;
import hu.unideb.webdev.repository.FilmCategoryRepository;
import hu.unideb.webdev.repository.entity.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CategoryDaoImplTest {

    @Spy
    @InjectMocks
    private CategoryDaoImpl categoryDao;

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private FilmCategoryRepository filmCategoryRepository;

    @Test
    void createCategory() throws CategoryAlreadyExistsException {
        doReturn(false).when(categoryRepository)
                .existsCategoryEntityByName(anyString());
        categoryDao.createCategory(getCategory());
        verify(categoryRepository,times(1)).save(any());
        verify(categoryDao,times(1)).convertCategoryToCategoryEntity(any());
    }
    @Test
    void createCategoryWithCategoryAlreadyExists(){
        when(categoryRepository.existsCategoryEntityByName(anyString()))
                .thenReturn(true);
       assertThrows(CategoryAlreadyExistsException.class,()->categoryDao.createCategory(getCategory()));
    }


    @Test
    void testUpdateCategory() throws UnknownCategoryException, CategoryAlreadyExistsException {
        doReturn(Optional.of(getCategoryEntity())).when(categoryRepository)
                .findByName(anyString());
        doReturn(getCategoryEntity()).when(categoryDao)
                .convertCategoryToCategoryEntity(any());
        doReturn(false).when(categoryRepository)
                .existsCategoryEntityByName(anyString());
        categoryDao.updateCategory(getCategory(),getCategory());
        verify(categoryDao,times(1)).convertCategoryToCategoryEntity(any());
        verify(categoryRepository,times(1)).save(any());
    }
    @Test
    void testUpdateCategoryWithUnknownCategory(){
        doReturn(Optional.empty()).when(categoryRepository)
                .findByName(anyString());
        assertThrows(UnknownCategoryException.class,()-> categoryDao.updateCategory(getCategory(),getCategory()));
    }
    @Test
    void testUpdateCategoryWithCategoryAlreadyExists() {
        doReturn(true).when(categoryRepository)
                .existsCategoryEntityByName(anyString());
        doReturn(Optional.of(getCategoryEntity()))
                .when(categoryRepository).findByName(anyString());
        assertThrows(CategoryAlreadyExistsException.class,()-> categoryDao.updateCategory(getCategory(),getCategory()));
        verify(categoryRepository,times(1)).findByName(anyString());
    }

    @Test
    void deleteCategory() throws UnknownCategoryException {
        doReturn(Optional.of(getCategoryEntity()))
                .when(categoryRepository).findByName(anyString());
        categoryDao.deleteCategory(getCategory().getName());
        verify(filmCategoryRepository,times(1)).findByCategory(any());
        verify(filmCategoryRepository,times(0)).delete(any());
        verify(categoryRepository,times(1)).delete(any());
    }
    @Test
    void deleteCategoryWithUnknownCategoryName(){
        doReturn(Optional.empty()).when(categoryRepository)
                .findByName(anyString());
        assertThrows(UnknownCategoryException.class,()->categoryDao.deleteCategory(getCategory().getName()));
    }

    @Test
    void getCategoryByName() throws UnknownCategoryException {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.ofNullable(getCategoryEntity()));
        categoryDao.getCategoryByName(getCategory().getName());
        verify(categoryDao,times(1)).convertCategoryEntityToCategory(any());
    }
    @Test
    void getCategoryByIdWithUnknownCategoryName() {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        assertThrows(UnknownCategoryException.class,()->categoryDao.getCategoryByName(getCategory().getName()));
        verify(categoryDao,times(0)).convertCategoryEntityToCategory(any());
    }

    @Test
    void readAll() {
        categoryDao.readAll();
        verify(categoryRepository,times(1)).findAll();
    }

    private Category getCategory(){
        return Category.builder()
                .name("Action")
                .filmCount(5)
                .build();
    }

    private CategoryEntity getCategoryEntity(){
        return CategoryEntity.builder()
                .id(1)
                .name("Action")
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
    }
}