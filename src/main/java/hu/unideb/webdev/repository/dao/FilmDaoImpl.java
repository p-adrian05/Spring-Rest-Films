package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.repository.*;
import hu.unideb.webdev.repository.entity.CategoryEntity;
import hu.unideb.webdev.repository.entity.FilmEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
@Repository
public class FilmDaoImpl implements FilmDao{

    private final FilmRepository filmRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void createFilm(Film film) {

    }

    @Override
    public void deleteFilm(Film film) {

    }

    @Override
    public void updateFilm(Film film) {

    }

    public List<String> queryCategories(FilmEntity filmEntity) {
        return categoryRepository.findByFilm(filmEntity.getId()).stream().map(CategoryEntity::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Film> readAll() {
        return StreamSupport.stream(filmRepository.findAll().spliterator(),true)
                .map(entity -> Film.builder()
                            .id(entity.getId())
                            .title(entity.getTitle())
                            .specialFeatures(entity.getSpecialFeatures())
                            .description(entity.getDescription())
                            .language(entity.getLanguageEntity().getName())
                            .originalLanguage(entity.getOriginalLanguageEntity() !=null ?
                                    entity.getOriginalLanguageEntity().getName() : null )
                            .rating(entity.getRating())
                            .length(entity.getLength())
                            .releaseYear(entity.getReleaseYear())
                            .replacementCost(entity.getReplacementCost())
                            .rentalDuration(entity.getRentalDuration())
                            .rentalRate(entity.getRentalRate())
                            .categories(queryCategories(entity))
                        .build())
                .collect(Collectors.toList());
    }
}
