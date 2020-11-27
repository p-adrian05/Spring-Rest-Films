package hu.unideb.webdev.controller;

import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.controller.dto.FilmDto;
import hu.unideb.webdev.exceptions.UnknownCategoryException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.exceptions.UnknownRateException;
import hu.unideb.webdev.exceptions.UnknownSpecialFeatureException;
import hu.unideb.webdev.repository.util.Rate;
import hu.unideb.webdev.repository.util.SpecialFeature;
import hu.unideb.webdev.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;
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
        log.info("Films with name: {}, {}",name,films);
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
    public Collection<FilmDto> listFilmsByCategory(@PathVariable String category){
        Collection<Film> films = filmService.getFilmsInCategory(category);
        if(films.size()>0){
            return films.stream()
                    .map(this::convertFilmToFilmDto)
                    .collect(Collectors.toList());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("No films found in category : %s",category));
    }
    @PostMapping("/film")
    public void record(@Valid @RequestBody FilmDto filmDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult
                    .getAllErrors().stream()
                    .map(error-> Objects.requireNonNull(error.getCodes())[0] +" "+ error.getDefaultMessage())
                    .collect(Collectors.toList());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    String.valueOf(errors));
        }
         try{
             filmService.recordFilm(convertFilmDtoToFilm(filmDto));
         }catch (UnknownCategoryException e) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                     String.format("No category found : %s",filmDto.getCategories()));
         }
    }
    @PutMapping("/film")
    public void updateFilm(@Valid @RequestBody FilmDto filmDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult
                    .getAllErrors().stream()
                    .map(error-> Objects.requireNonNull(error.getCodes())[0] +" "+ error.getDefaultMessage())
                    .collect(Collectors.toList());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    String.valueOf(errors));
        }
        try{
            filmService.updateFilm(convertFilmDtoToFilm(filmDto));
        }catch (UnknownCategoryException | UnknownFilmException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Fail to update Film: %s",e.getMessage()));
        }

    }

    @DeleteMapping("/film")
    public void deleteFilm(@RequestParam(name = "id") int id){
        try {
           filmService.deleteFilm(id);
        } catch (UnknownFilmException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
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

    private Film convertFilmDtoToFilm(FilmDto filmDto){
        try {
            List<SpecialFeature> specialFeatures = new LinkedList<>();
            for(String s : filmDto.getSpecialFeatures()) {
                specialFeatures.add(SpecialFeature.convertStringToSpecialFeature(s));
            }
            return  Film.builder()
                    .id(filmDto.getId())
                    .categories(filmDto.getCategories())
                    .rentalRate(filmDto.getRentalRate())
                    .rentalDuration(filmDto.getRentalDuration())
                    .replacementCost(filmDto.getReplacementCost())
                    .releaseYear(filmDto.getReleaseYear())
                    .length(filmDto.getLength())
                    .rating(Rate.convertStringToRate(filmDto.getRating()))
                    .originalLanguage(filmDto.getOriginalLanguage())
                    .language(filmDto.getLanguage())
                    .description(filmDto.getDescription())
                    .title(filmDto.getTitle())
                    .specialFeatures(specialFeatures)
                    .build();
        } catch (UnknownRateException | UnknownSpecialFeatureException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Fail to create new Film : %s",e.getMessage()));
        }
    }



}
