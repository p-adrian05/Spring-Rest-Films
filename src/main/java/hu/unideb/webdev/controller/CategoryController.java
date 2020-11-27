package hu.unideb.webdev.controller;

import hu.unideb.webdev.Model.Category;
import hu.unideb.webdev.controller.dto.CategoryDto;
import hu.unideb.webdev.controller.dto.UpdateCategoryRequestDto;
import hu.unideb.webdev.exceptions.CategoryAlreadyExistsException;
import hu.unideb.webdev.exceptions.UnknownCategoryException;
import hu.unideb.webdev.repository.dao.CategoryDao;
import hu.unideb.webdev.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public Collection<CategoryDto> listCategories(){
        return categoryService.getAllCategories()
                .stream()
                .map(this::convertCategoryToDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/category/{name}")
    public CategoryDto getCategoryByName(@PathVariable String name){
        try {
            return convertCategoryToDto(categoryService.getCategoryByName(name));
        } catch (UnknownCategoryException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    @PutMapping("/category")
    public void updateCategory(@RequestBody UpdateCategoryRequestDto dto ) {
        try {
            categoryService.updateCategory(new Category(0,dto.getOldCategoryName()),new Category(0,dto.getNewCategoryName()));
        } catch (UnknownCategoryException | CategoryAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,e.getMessage());
        }
    }
    @PostMapping("/category")
    public void createCategory(@RequestBody CategoryDto categoryDto) {
        try {
            categoryService.recordCategory(convertCategoryDtoToCategory(categoryDto));
        } catch (CategoryAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,e.getMessage());
        }
    }
    @DeleteMapping("/category")
    public void deleteCategory(@RequestBody CategoryDto categoryDto) {
        try {
            categoryService.deleteCategory(convertCategoryDtoToCategory(categoryDto));
        } catch (UnknownCategoryException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,e.getMessage());
        }
    }

    private CategoryDto convertCategoryToDto(Category category) {
        return new CategoryDto(category.getName());
    }

    private Category convertCategoryDtoToCategory(CategoryDto categoryDto) {
        return new Category(0,categoryDto.getName());
    }

}
