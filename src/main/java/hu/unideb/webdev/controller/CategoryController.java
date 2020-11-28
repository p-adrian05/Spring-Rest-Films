package hu.unideb.webdev.controller;

import hu.unideb.webdev.Model.Category;
import hu.unideb.webdev.controller.dto.CategoryDto;
import hu.unideb.webdev.controller.dto.UpdateCategoryRequestDto;
import hu.unideb.webdev.exceptions.CategoryAlreadyExistsException;
import hu.unideb.webdev.exceptions.UnknownCategoryException;
import hu.unideb.webdev.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
    public void updateCategory(@Valid @RequestBody UpdateCategoryRequestDto updateCategoryDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){

        }
        try {
            categoryService.updateCategory(new Category(updateCategoryDto.getOldCategoryName(),0)
                    ,new Category(updateCategoryDto.getNewCategoryName(),0));
        } catch (UnknownCategoryException | CategoryAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
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
    public void deleteCategory(@RequestParam String name) {
        try {
            categoryService.deleteCategory(name);
        } catch (UnknownCategoryException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,e.getMessage());
        }
    }

    private CategoryDto convertCategoryToDto(Category category) {
        return new CategoryDto(category.getName(),category.getFilmCount());
    }

    private Category convertCategoryDtoToCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getName(),0);
    }

}
