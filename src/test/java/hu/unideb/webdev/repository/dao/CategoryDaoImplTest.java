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
        doReturn(getCategoryEntity()).when(categoryDao)
                .convertCategoryToCategoryEntity(any());
        categoryDao.createCategory(getCategory());
        verify(categoryRepository,times(1)).findByName(anyString());
        verify(categoryRepository,times(1)).save(any());
    }
    @Test
    void createCategoryWithCategoryAlreadyExists(){
        when(categoryRepository.findByName(anyString()))
                .thenReturn(Optional.of(getCategoryEntity()));
       assertThrows(CategoryAlreadyExistsException.class,()->categoryDao.createCategory(getCategory()));
    }


    @Test
    void testUpdateCategory() throws UnknownCategoryException, CategoryAlreadyExistsException {
        doReturn(true).when(categoryRepository)
                .existsById(anyInt());
        doReturn(Optional.empty())
                .when(categoryRepository).findByName(anyString());
        doReturn(getCategoryEntity()).when(categoryDao)
                .convertCategoryToCategoryEntity(any());
        categoryDao.updateCategory(getCategory(),getCategory());
        verify(categoryRepository,times(1)).existsById(anyInt());
        verify(categoryRepository,times(1)).findByName(anyString());
        verify(categoryDao,times(1)).convertCategoryToCategoryEntity(any());
        verify(categoryRepository,times(1)).save(any());
    }
    @Test
    void testUpdateCategoryWithUnknownCategory() {
        doReturn(false).when(categoryRepository)
                .existsById(anyInt());
        assertThrows(UnknownCategoryException.class,()-> categoryDao.updateCategory(getCategory(),getCategory()));
        verify(categoryRepository,times(1)).existsById(anyInt());
    }
    @Test
    void testUpdateCategoryWithCategoryAlreadyExists() {
        doReturn(true).when(categoryRepository)
                .existsById(anyInt());
        doReturn(Optional.of(getCategoryEntity()))
                .when(categoryRepository).findByName(anyString());
        assertThrows(CategoryAlreadyExistsException.class,()-> categoryDao.updateCategory(getCategory(),getCategory()));
        verify(categoryRepository,times(1)).existsById(anyInt());
        verify(categoryRepository,times(1)).findByName(anyString());
    }

    @Test
    void deleteCategory() throws UnknownCategoryException {
        doReturn(true).when(categoryRepository)
                .existsById(anyInt());
        doReturn(Optional.of(getCategoryEntity()))
                .when(categoryRepository).findByName(anyString());
        categoryDao.deleteCategory(getCategory().getName());

        verify(categoryDao,times(1)).convertCategoryToCategoryEntity(any());
        verify(filmCategoryRepository,times(1)).findByCategory(any());
        verify(filmCategoryRepository,times(0)).delete(any());
        verify(categoryRepository,times(1)).delete(any());
    }
    @Test
    void deleteCategoryWithUnknownCategoryId(){
        doReturn(false).when(categoryRepository)
                .existsById(anyInt());
        assertThrows(UnknownCategoryException.class,()->categoryDao.deleteCategory(getCategory().getName()));
    }
    @Test
    void deleteCategoryWithUnknownCategoryName(){
        doReturn(true).when(categoryRepository)
                .existsById(anyInt());
        doReturn(Optional.empty())
                .when(categoryRepository).findByName(anyString());
        assertThrows(UnknownCategoryException.class,()->categoryDao.deleteCategory(getCategory().getName()));
    }

    @Test
    void getCategoryByName() throws UnknownCategoryException {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.ofNullable(getCategoryEntity()));
        categoryDao.getCategoryByName(getCategory().getName());
        verify(categoryDao,times(1)).convertCategoryEntityToCategory(any());
    }
    @Test
    void getCategoryByIdWithUnknownCategory() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());
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