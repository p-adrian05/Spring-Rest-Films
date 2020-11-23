package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.repository.*;
import hu.unideb.webdev.repository.entity.CategoryEntity;
import hu.unideb.webdev.repository.entity.FilmCategoryEntity;
import hu.unideb.webdev.repository.entity.FilmEntity;
import hu.unideb.webdev.repository.entity.LanguageEntity;
import hu.unideb.webdev.repository.util.UnknownCategoryException;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
@Repository
public class FilmDaoImpl implements FilmDao {

    private final FilmRepository filmRepository;
    private final CategoryRepository categoryRepository;
    private final LanguageRepository languageRepository;
    private final FilmCategoryRepository filmCategoryRepository;

    @Override
    public void createFilm(Film film) throws UnknownCategoryException {
        FilmEntity filmEntity = FilmEntity.builder()
                .title(film.getTitle())
                .description(film.getDescription())
                .releaseYear(film.getReleaseYear())
                .languageEntity(queryLanguage(film.getLanguage()))
                .originalLanguageEntity(queryLanguage(film.getOriginalLanguage()))
                .rentalDuration(film.getRentalDuration())
                .rentalRate(film.getRentalRate())
                .length(film.getLength())
                .replacementCost(film.getReplacementCost())
                .rating(film.getRating())
                .specialFeatures(film.getSpecialFeatures())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();

        log.info("FilmEntity: {}",filmEntity);
        List<CategoryEntity> categoryEntities = new LinkedList<>();
        for(String categoryName : film.getCategories()){
            categoryEntities.add(queryCategory(categoryName));
        }
        try{
            filmRepository.save(filmEntity);
            categoryEntities.forEach(categoryEntity ->
                filmCategoryRepository
                        .save(new FilmCategoryEntity(categoryEntity,
                                filmEntity,new Timestamp((new Date()).getTime()))));
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFilm(Film film) {

    }

    @Override
    public void updateFilm(Film film) {

    }

    protected List<String> queryCategories(int filmId) {
        return categoryRepository.findByFilmId(filmId).stream().map(CategoryEntity::getName)
                .collect(Collectors.toList());
    }
    protected CategoryEntity queryCategory(String categoryName) throws UnknownCategoryException {
        Optional<CategoryEntity> categoryEntity =
                categoryRepository.findByName(categoryName);
        if(categoryEntity.isEmpty()){
            throw new UnknownCategoryException(categoryName);
        }
        log.info("Category Entity: {}",categoryEntity);
        return categoryEntity.get();
    }

    protected LanguageEntity queryLanguage(String languageName){
        Optional<LanguageEntity> languageEntity = languageRepository.findByName(languageName);
        if(languageEntity.isEmpty()){
            languageEntity = Optional.ofNullable(LanguageEntity.builder()
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .name(languageName)
                    .build());
            languageRepository.save(languageEntity.get());
            log.info("Recorded bew Language: {}",languageName);
        }
        log.trace("Language Entity: {}",languageEntity);
        return languageEntity.get();
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
                            .categories(queryCategories(entity.getId()))
                        .build())
                .collect(Collectors.toList());
    }
}
