package hu.unideb.webdev.controller;

import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.controller.dto.ActorAndFilmsDto;
import hu.unideb.webdev.controller.dto.ActorDto;
import hu.unideb.webdev.controller.dto.ActorFilmDto;
import hu.unideb.webdev.exceptions.UnknownActorException;
import hu.unideb.webdev.exceptions.UnknownFilmException;

import hu.unideb.webdev.service.ActorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @GetMapping("/actors")
    public Collection<ActorDto> listActors(){
        return actorService.getAllActors().parallelStream().map(this::convertActorToActorDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/actor")
    public ActorAndFilmsDto getActorById(@RequestParam(name = "id") int actorId){
        try {
            Actor actor = actorService.getActorById(actorId);
            return convertActorToActorAndFilmsDto(actor);
        } catch (UnknownActorException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    @GetMapping("/actor/{name}")
    public Collection<ActorAndFilmsDto> getActorByName(@PathVariable String name){
        try {
            Collection<Actor> actors = actorService.getActorsByName(name);
            return actors.stream().map(this::convertActorToActorAndFilmsDto)
                    .collect(Collectors.toList());
        } catch (UnknownActorException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    @GetMapping("/actor/film/{filmId}")
    public Collection<ActorDto> getActorsByFilmId(@PathVariable(name = "filmId") int filmId){
            Collection<Actor> actors = actorService.getActorsByFilmId(filmId);
            return actors.stream().map(this::convertActorToActorDto)
                    .collect(Collectors.toList());
    }
    @PutMapping("/actor")
    public void updateActor(@RequestBody ActorAndFilmsDto actorAndFilmsDto){
        try{
            actorService.updateActor(convertActorAndFilmsDtoToActor(actorAndFilmsDto));
        } catch (UnknownActorException | UnknownFilmException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Wrong data");
        }
    }
    @DeleteMapping("/actor")
    public void deleteActor(@RequestParam(name = "id") int id){
        try {
            actorService.deleteActor(id);
        } catch (UnknownActorException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @PostMapping("/actor")
    public void recordActor(@RequestBody ActorAndFilmsDto actorAndFilmsDto){
        try{
            actorService.recordActor(convertActorAndFilmsDtoToActor(actorAndFilmsDto));
        } catch (UnknownFilmException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,e.getMessage());
        }
    }


    private ActorAndFilmsDto convertActorToActorAndFilmsDto(Actor actor){
        List<ActorFilmDto> actorFilmDtos = new LinkedList<>();
        actor.getFilms().forEach(film -> actorFilmDtos.add(convertFilmToActorFilmDto(film)));
        return ActorAndFilmsDto.builder()
                .firstName(actor.getFirstName())
                .lastName(actor.getLastName())
                .id(actor.getId())
                .films(actorFilmDtos)
                .build();

    }
    private Actor convertActorAndFilmsDtoToActor(ActorAndFilmsDto actorAndFilmsDto){
        List<Film> films = new LinkedList<>();
        actorAndFilmsDto.getFilms().forEach(filmDto -> films.add(convertActorFilmDtoToFilm(filmDto)));
        return Actor.builder()
                .firstName(actorAndFilmsDto.getFirstName())
                .lastName(actorAndFilmsDto.getLastName())
                .id(actorAndFilmsDto.getId())
                .films(films)
                .build();

    }
    private ActorDto convertActorToActorDto(Actor actor){
        return ActorDto.builder()
                .firstName(actor.getFirstName())
                .lastName(actor.getLastName())
                .id(actor.getId())
                .build();

    }

    private ActorFilmDto convertFilmToActorFilmDto(Film film){
        return  ActorFilmDto.builder()
                .id(film.getId())
                .releaseYear(film.getReleaseYear())
                .rating(film.getRating().toString())
                .language(film.getLanguage())
                .description(film.getDescription())
                .title(film.getTitle())
                .build();
    }
    private Film convertActorFilmDtoToFilm(ActorFilmDto film){
        return  Film.builder()
                .id(film.getId())
                .releaseYear(film.getReleaseYear())
                .language(film.getLanguage())
                .description(film.getDescription())
                .title(film.getTitle())
                .build();
    }
}
