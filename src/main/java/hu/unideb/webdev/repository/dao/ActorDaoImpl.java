package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.repository.ActorRepository;
import hu.unideb.webdev.repository.FilmActorRepository;
import hu.unideb.webdev.repository.FilmRepository;
import hu.unideb.webdev.repository.entity.*;
import hu.unideb.webdev.repository.util.UnknownActorException;
import hu.unideb.webdev.repository.util.UnknownCategoryException;
import hu.unideb.webdev.repository.util.UnknownFilmException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Slf4j
@Repository
public class ActorDaoImpl implements ActorDao{

    private final FilmActorRepository filmActorRepository;
    private final ActorRepository actorRepository;
    private final FilmRepository filmRepository;


    @Override
    public int createActor(Actor actor) throws UnknownFilmException {
        ActorEntity actorEntity = convertActorToActorEntity(actor);
        log.info("ActorEntity: {}",actorEntity);
        List<FilmEntity> filmEntities = new LinkedList<>();
        for(Film film : actor.getFilms()){
            filmEntities.add(queryFilm(film));
        }
        try{
            actorRepository.save(actorEntity);
            filmEntities.forEach(filmEntity ->
                    filmActorRepository
                            .save(new FilmActorEntity(actorEntity,
                                    filmEntity,new Timestamp((new Date()).getTime()))));
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return actorEntity.getId();
    }

    @Override
    public void deleteActor(Actor actor) throws UnknownActorException {
        Optional<ActorEntity> actorEntity = actorRepository.findById(actor.getId());
        if(actorEntity.isEmpty()){
            throw new UnknownActorException(String.format("Actor Not Found %s",actor), actor);
        }
        List<FilmActorEntity> filmActorEntities = filmActorRepository.findByActor(actorEntity.get());
        filmActorEntities.forEach(filmActorRepository::delete);
        actorRepository.delete(actorEntity.get());
    }

    @Override
    public void updateActor(Actor actor) throws UnknownActorException, UnknownFilmException {
        List<Film> oldFilms = getActorById(actor.getId()).getFilms();
        ActorEntity actorEntity = convertActorToActorEntity(actor);
        log.info("Updated actorEntity: {}",actorEntity);
        List<Film> newFilms = actor.getFilms().stream()
                .filter(film -> !oldFilms.contains(film))
                .collect(Collectors.toList());
        List<Film> deletedFilms = oldFilms.stream()
                .filter(film -> !actor.getFilms().contains(film) && !newFilms.contains(film))
                .collect(Collectors.toList());
        log.info("New Films: {}",newFilms);
        for(Film film : newFilms){
            filmActorRepository
                    .save(new FilmActorEntity(actorEntity,queryFilm(film)
                            ,new Timestamp((new Date()).getTime())));
        }
        log.info("deleted Films: {}",deletedFilms);
        for(Film film : deletedFilms){
            filmActorRepository
                    .delete(filmActorRepository.findByFilmAndActor(queryFilm(film),actorEntity));
        }
        try{
            actorRepository.save(actorEntity);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    public Actor getActorById(int actorId) throws UnknownActorException {
        Optional<ActorEntity> actorEntity = actorRepository.findById(actorId);
        if(actorEntity.isEmpty()){
            throw new UnknownActorException(String.format("Actor is not found %s",actorId));
        }
        return convertActorEntityToActor(actorEntity.get());
    }

    @Override
    public Collection<Actor> readAll() {
        return StreamSupport.stream(actorRepository.findAll().spliterator(),true)
                .map(this::convertActorEntityToActor)
                .collect(Collectors.toList());
    }

    protected List<Film> queryFilms(int actorId){
        return filmRepository.findByActorId(actorId).stream().map(filmEntity -> Film.builder()
                .id(filmEntity.getId())
                .title(filmEntity.getTitle())
                .specialFeatures(filmEntity.getSpecialFeatures())
                .description(filmEntity.getDescription())
                .language(filmEntity.getLanguageEntity().getName())
                .originalLanguage(filmEntity.getOriginalLanguageEntity() !=null ?
                        filmEntity.getOriginalLanguageEntity().getName() : null )
                .rating(filmEntity.getRating())
                .length(filmEntity.getLength())
                .releaseYear(filmEntity.getReleaseYear())
                .replacementCost(filmEntity.getReplacementCost())
                .rentalDuration(filmEntity.getRentalDuration())
                .rentalRate(filmEntity.getRentalRate())
                .build()).collect(Collectors.toList());
    }
    protected FilmEntity queryFilm(Film film) throws UnknownFilmException {
        Optional<FilmEntity> filmEntity =
                filmRepository.findById(film.getId());
        if(filmEntity.isEmpty()){
            throw new UnknownFilmException("Unknown film ",film);
        }
        log.info("Film Entity: {}",filmEntity.get());
        return filmEntity.get();
    }
    protected Actor convertActorEntityToActor(ActorEntity actorEntity){
        return Actor.builder()
                .id(actorEntity.getId())
                .firstName(actorEntity.getFirstName())
                .lastName(actorEntity.getLastName())
                .films(queryFilms(actorEntity.getId())).build();
    }
    protected ActorEntity convertActorToActorEntity(Actor actor){
        return ActorEntity.builder()
                .id(actor.getId())
                .firstName(actor.getFirstName())
                .lastName(actor.getLastName())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
    }
}
