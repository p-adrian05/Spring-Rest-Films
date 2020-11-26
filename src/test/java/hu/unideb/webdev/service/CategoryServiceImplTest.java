package hu.unideb.webdev.service;

import hu.unideb.webdev.Model.Category;
import hu.unideb.webdev.exceptions.CategoryAlreadyExistsException;
import hu.unideb.webdev.exceptions.UnknownCategoryException;
import hu.unideb.webdev.repository.dao.CategoryDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryDao categoryDao;

    @Test
    void getAllCategories() {
        when(categoryDao.readAll()).thenReturn(getCategories());
        Collection<Category> categories = categoryService.getAllCategories();
        assertThat(getCategories(),is(categories));
    }

    @Test
    void recordCategory() throws CategoryAlreadyExistsException {
        Category category = getCategory();

        categoryService.recordCategory(category);

        verify(categoryDao,times(1)).createCategory(category);
    }

    @Test
    void testRecordCategoryWithCategoryAlreadyExists() throws CategoryAlreadyExistsException {
        doThrow(CategoryAlreadyExistsException.class).when(categoryDao).createCategory(any());
        assertThrows(CategoryAlreadyExistsException.class,()->
                categoryService.recordCategory(getCategory()));
    }

    @Test
    void deleteCategory() throws UnknownCategoryException {
        Category category = getCategory();

        categoryService.deleteCategory(category);

        verify(categoryDao,times(1)).deleteCategory(category);

    }

    @Test
    void testDeleteCategoryWithCategoryAlreadyExists() throws UnknownCategoryException {
        doThrow(UnknownCategoryException.class).when(categoryDao).deleteCategory(any());
        assertThrows(UnknownCategoryException.class,()->
                categoryService.deleteCategory(getCategory()));
    }

    @Test
    void updateCategory() throws UnknownCategoryException, CategoryAlreadyExistsException {
        Category category = getCategory();

        categoryService.updateCategory(category);

        verify(categoryDao,times(1)).updateCategory(category);
    }
    @Test
    void testUpdateCategoryWithCategoryAlreadyExists() throws UnknownCategoryException, CategoryAlreadyExistsException {
        doThrow(CategoryAlreadyExistsException.class).when(categoryDao).updateCategory(any());
        assertThrows(CategoryAlreadyExistsException.class,()->
                categoryService.updateCategory(getCategory()));
    }
    @Test
    void testUpdateCategoryWithUnknownCategory() throws UnknownCategoryException, CategoryAlreadyExistsException {
        doThrow(UnknownCategoryException.class).when(categoryDao).updateCategory(any());
        assertThrows(UnknownCategoryException.class,()->
                categoryService.updateCategory(getCategory()));
    }

    @Test
    void getCategoryById() throws UnknownCategoryException {
        when(categoryDao.getCategoryById(anyInt())).thenReturn(getCategory());
        Category category = categoryService.getCategoryById(0);
        assertThat(getCategory(),is(category));
    }
    @Test
    void testGetCategoryByIdWithUnknownCategory() throws UnknownCategoryException{
        doThrow(UnknownCategoryException.class).when(categoryDao).getCategoryById(anyInt());
        assertThrows(UnknownCategoryException.class,()->
                categoryService.getCategoryById(1));
    }
    private Category getCategory(){
        return Category.builder()
                .id(0)
                .name("Test category")
                .build();
    }
    private Collection<Category> getCategories(){
        return List.of(getCategory(),getCategory());
    }
}