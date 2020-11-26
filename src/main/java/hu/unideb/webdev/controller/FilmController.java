package hu.unideb.webdev.controller;

import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.controller.dto.FilmDto;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.repository.util.SpecialFeature;
import hu.unideb.webdev.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FilmController {

    private final FilmService filmService;


    @GetMapping("/films")
    public Collection<FilmDto> listFilms(){
        return filmService.getAllFilm()
                .parallelStream()
                .map(this::convertFilmToFilmDto)
                .collect(Collectors.toList());
    }


    @GetMapping("/film/{name}")
    public Collection<FilmDto> getFilmsByName(@PathVariable(name = "name") String name){
        Collection<Film> films = filmService.getFilmsByName(name);
        if(films.size()>0){
            return films.stream()
                    .map(this::convertFilmToFilmDto)
                    .collect(Collectors.toList());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("No films found with name : %s",name));
    }
    @GetMapping("/film")
    public FilmDto getFilmById(@RequestParam(name = "id") int id){
        try {
            return convertFilmToFilmDto(filmService.getFilmById(id));
        } catch (UnknownFilmException e) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    @GetMapping("/films/{category}")
    public Collection<FilmDto> listFilms(@PathVariable String category){
        Collection<Film> films = filmService.getFilmsInCategory(category);
        if(films.size()>0){
            return films.stream()
                    .map(this::convertFilmToFilmDto)
                    .collect(Collectors.toList());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("No films found in category : %s",category));
    }
    private FilmDto convertFilmToFilmDto(Film film){
        return  FilmDto.builder()
                .id(film.getId())
                .categories(film.getCategories())
                .rentalRate(film.getRentalRate())
                .rentalDuration(film.getRentalDuration())
                .replacementCost(film.getReplacementCost())
                .releaseYear(film.getReleaseYear())
                .length(film.getLength())
                .rating(film.getRating().toString())
                .originalLanguage(film.getOriginalLanguage())
                .language(film.getLanguage())
                .description(film.getDescription())
                .title(film.getTitle())
                .specialFeatures(film.getSpecialFeatures()
                        .stream().map(SpecialFeature::toString)
                        .collect(Collectors.toList()))
                .build();
    }



}
