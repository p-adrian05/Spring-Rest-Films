package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.exceptions.UnknownCategoryException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.repository.*;
import hu.unideb.webdev.repository.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
@Repository
public class FilmDaoImpl implements FilmDao {

    private final FilmRepository filmRepository;
    private final CategoryRepository categoryRepository;
    private final LanguageRepository languageRepository;
    private final FilmCategoryRepository filmCategoryRepository;
    private final FilmActorRepository filmActorRepository;

    @Override
    @Transactional
    public int createFilm(Film film) throws UnknownCategoryException { ;
        FilmEntity filmEntity = convertFilmToFilmEntity(film);
        log.info("FilmEntity: {}", filmEntity);
        List<CategoryEntity> categoryEntities = new LinkedList<>();
        for (String categoryName : film.getCategories()) {
            categoryEntities.add(queryCategory(categoryName));
        }
        log.info("Category entities : {}", categoryEntities);
        try {
            filmRepository.save(filmEntity);
            categoryEntities.forEach(categoryEntity ->
                    filmCategoryRepository
                            .save(new FilmCategoryEntity(categoryEntity,
                                    filmEntity, new Timestamp((new Date()).getTime()))));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return filmEntity.getId();
    }

    @Override
    @Transactional
    public void deleteFilm(Film film) throws UnknownFilmException {
        Optional<FilmEntity> filmEntity = filmRepository.findById(film.getId());
        if (filmEntity.isEmpty()) {
            throw new UnknownFilmException(String.format("Film Not Found %s", film), film);
        }
        log.info("Deleted film {}", filmEntity.get());
        List<FilmCategoryEntity> filmCategoryEntities = filmCategoryRepository.findByFilm(filmEntity.get());
        log.info("Deleted film-category connections: {}", filmCategoryEntities.size());
        List<FilmActorEntity> filmActorEntities = filmActorRepository.findByFilm(filmEntity.get());
        log.info("Deleted film-actor connections: {}", filmActorEntities.size());
        filmCategoryEntities.forEach(filmCategoryRepository::delete);
        filmActorEntities.forEach(filmActorRepository::delete);
        filmRepository.delete(filmEntity.get());
    }

    @Override
    @Transactional
    public void updateFilm(Film film) throws UnknownCategoryException, UnknownFilmException {
        List<String> oldFilmCategories = getFilmById(film.getId()).getCategories();
        FilmEntity filmEntity = convertFilmToFilmEntity(film);
        log.info("Updated Film: {}", film);
        log.info("Old Categories: {}", oldFilmCategories);
        List<String> newCategoryNames = film.getCategories().stream()
                .filter(categoryName -> !oldFilmCategories.contains(categoryName))
                .collect(Collectors.toList());
        List<String> deletedCategoryNames = oldFilmCategories.stream()
                .filter(categoryName -> !film.getCategories().contains(categoryName)
                        && !newCategoryNames.contains(categoryName))
                .collect(Collectors.toList());
        log.info("New Categories: {}", newCategoryNames);
        for (String categoryName : newCategoryNames) {
            filmCategoryRepository
                    .save(new FilmCategoryEntity(queryCategory(categoryName),
                            filmEntity, new Timestamp((new Date()).getTime())));
        }
        log.info("Deleted Categories: {}", deletedCategoryNames);
        for (String categoryName : deletedCategoryNames) {
            filmCategoryRepository
                    .delete(filmCategoryRepository.findByFilmAndCategory(filmEntity, queryCategory(categoryName)));
        }
        try {
            filmRepository.save(filmEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public Collection<Film> readAll() {
        return StreamSupport.stream(filmRepository.findAll().spliterator(), true)
                .map(this::convertFilmEntityToFilm)
                .collect(Collectors.toList());
    }

    @Override
    public Film getFilmById(int filmId) throws UnknownFilmException {
        Optional<FilmEntity> filmEntity = filmRepository.findById(filmId);
        if (filmEntity.isPresent()) {
            log.info("Film entity by id {}, {}", filmId, filmEntity.get());
            return convertFilmEntityToFilm(filmEntity.get());
        } else {
            throw new UnknownFilmException(String.format("Film is not found by id: %s",filmId));
        }
    }
    @Override
    public Collection<Film> getFilmsByName(String name) {
       return StreamSupport.stream(filmRepository.findByTitle(name).spliterator(), true)
                .map(this::convertFilmEntityToFilm)
                .collect(Collectors.toList());
    }

    protected Film convertFilmEntityToFilm(FilmEntity filmEntity) {
        return Film.builder()
                .id(filmEntity.getId())
                .title(filmEntity.getTitle())
                .specialFeatures(filmEntity.getSpecialFeatures())
                .description(filmEntity.getDescription())
                .language(filmEntity.getLanguageEntity().getName())
                .originalLanguage(filmEntity.getOriginalLanguageEntity() != null ?
                        filmEntity.getOriginalLanguageEntity().getName() : null)
                .rating(filmEntity.getRating())
                .length(filmEntity.getLength())
                .releaseYear(filmEntity.getReleaseYear())
                .replacementCost(filmEntity.getReplacementCost())
                .rentalDuration(filmEntity.getRentalDuration())
                .rentalRate(filmEntity.getRentalRate())
                .categories(queryCategories(filmEntity.getId()))
                .build();
    }

    protected FilmEntity convertFilmToFilmEntity(Film film) {
        return FilmEntity.builder()
                .id(film.getId())
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
    }


    private List<String> queryCategories(int filmId) {
        return categoryRepository.findByFilmId(filmId).stream().map(CategoryEntity::getName)
                .collect(Collectors.toList());
    }

    protected CategoryEntity queryCategory(String categoryName) throws UnknownCategoryException {
        Optional<CategoryEntity> categoryEntity =
                categoryRepository.findByName(categoryName);
        if (categoryEntity.isEmpty()) {
            throw new UnknownCategoryException("Unknown category ", categoryName);
        }
        log.info("Category Entity: {}", categoryEntity);
        return categoryEntity.get();
    }

    protected LanguageEntity queryLanguage(String languageName) {
        Optional<LanguageEntity> languageEntity = languageRepository.findByName(languageName);
        if (languageEntity.isEmpty()) {
            languageEntity = Optional.ofNullable(LanguageEntity.builder()
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .name(languageName)
                    .build());
            languageRepository.save(languageEntity.get());
            log.info("Recorded bew Language: {}", languageName);
        }
        log.trace("Language Entity: {}", languageEntity);
        return languageEntity.get();
    }
}
