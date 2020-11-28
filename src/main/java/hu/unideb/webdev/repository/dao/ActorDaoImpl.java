package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.exceptions.UnknownActorException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.repository.ActorRepository;
import hu.unideb.webdev.repository.FilmActorRepository;
import hu.unideb.webdev.repository.FilmRepository;
import hu.unideb.webdev.repository.entity.ActorEntity;
import hu.unideb.webdev.repository.entity.FilmActorEntity;
import hu.unideb.webdev.repository.entity.FilmEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Slf4j
@Repository
public class ActorDaoImpl implements ActorDao {

    private final FilmActorRepository filmActorRepository;
    private final ActorRepository actorRepository;
    private final FilmRepository filmRepository;


    @Override
    @Transactional
    public int createActor(Actor actor) throws UnknownFilmException {
        ActorEntity actorEntity = convertActorToActorEntity(actor);
        log.info("ActorEntity: {}", actorEntity);
        List<FilmEntity> filmEntities = new LinkedList<>();
        for (Film film : actor.getFilms()) {
            filmEntities.add(queryFilm(film));
        }
        log.info("Actor films: {}, {}", actorEntity, filmEntities);
        try {
            actorRepository.save(actorEntity);
            filmEntities.forEach(filmEntity ->
                    filmActorRepository
                            .save(new FilmActorEntity(actorEntity,
                                    filmEntity, new Timestamp((new Date()).getTime()))));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return actorEntity.getId();
    }

    @Override
    @Transactional
    public void deleteActor(int actorId) throws UnknownActorException {
        Optional<ActorEntity> actorEntity = actorRepository.findById(actorId);
        if (actorEntity.isEmpty()) {
            throw new UnknownActorException(String.format("Actor Not Found id %s", actorId));
        }
        log.info("Deleted Actor: {}", actorEntity.get());
        List<FilmActorEntity> filmActorEntities = filmActorRepository.findByActor(actorEntity.get());
        log.info("Deleted Actor-Film connections: {}", filmActorEntities.size());
        filmActorEntities.forEach(filmActorRepository::delete);
        actorRepository.delete(actorEntity.get());
    }

    @Override
    @Transactional
    public void updateActor(Actor actor) throws UnknownActorException, UnknownFilmException {
        if(!actorRepository.existsById(actor.getId())){
            throw new UnknownActorException(String.format("Actor is not found %s", actor));
        }
        List<FilmEntity> oldFilmEntities = filmRepository.findByActorId(actor.getId());
        List<FilmEntity> actualFilmEntities = new LinkedList<>();
        for(Film film : actor.getFilms()){
            actualFilmEntities.add(queryFilm(film));
        }
        List<FilmEntity> newFilmEntities = actualFilmEntities.stream()
                .filter(film -> !oldFilmEntities.contains(film))
                .collect(Collectors.toList());
        List<FilmEntity> deletedFilmEntities = oldFilmEntities.stream()
                .filter(film -> !actualFilmEntities.contains(film) && !newFilmEntities.contains(film))
                .collect(Collectors.toList());
        ActorEntity actorEntity = convertActorToActorEntity(actor);
        try {
            newFilmEntities.forEach(filmEntity -> filmActorRepository
                    .save(new FilmActorEntity(actorEntity, filmEntity
                            ,new Timestamp((new Date()).getTime()))));
            deletedFilmEntities.forEach(filmEntity -> filmActorRepository
                    .delete(filmActorRepository.findByFilmAndActor(filmEntity, actorEntity)));
            actorRepository.save(actorEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("Updated actorEntity: {}", actorEntity);
        log.info("New Films: {}", newFilmEntities);
        log.info("deleted Films: {}", deletedFilmEntities);
    }

    @Override
    public Actor getActorById(int actorId) throws UnknownActorException {
        Optional<ActorEntity> actorEntity = actorRepository.findById(actorId);
        if (actorEntity.isEmpty()) {
            throw new UnknownActorException(String.format("Actor is not found %s", actorId));
        }
        Actor actor = convertActorEntityToActor(actorEntity.get());
        actor.setFilms(queryFilms(actorEntity.get().getId()));
        log.info("Actor entity by id {}, {}", actorId, actorEntity.get());
        return actor;
    }
    @Override
    public List<Actor> getActorsByName(String name) throws UnknownActorException {
        String[] firstAndLastName = name.split(" ");
        List<ActorEntity> actorEntities = new LinkedList<>();
        if(firstAndLastName.length == 1){
            actorEntities = actorRepository.findByFirstNameOrLastName(firstAndLastName[0],firstAndLastName[0]);
        }
        if(firstAndLastName.length >= 2){
            actorEntities = actorRepository.findByFirstNameAndLastName(firstAndLastName[0],firstAndLastName[1]);
        }
        if (actorEntities.isEmpty()) {
            throw new UnknownActorException(String.format("Actor is not found %s", name));
        }
        log.info("Actor entities by name {}, {}", name, actorEntities);
        return actorEntities.stream().map(actorEntity -> {
            Actor actor = convertActorEntityToActor(actorEntity);
           actor.setFilms(queryFilms(actorEntity.getId()));
           return actor;
        }).collect(Collectors.toList());
    }

    @Override
    public Collection<Actor> readAll() {
        return StreamSupport.stream(actorRepository.findAll().spliterator(), true)
                .map(this::convertActorEntityToActor)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Actor> getActorsByFilmId(int filmId){
       return actorRepository.findByFilmId(filmId).stream()
               .map(this::convertActorEntityToActor)
               .collect(Collectors.toList());
    }

    protected List<Film> queryFilms(int actorId) {
        return filmRepository.findByActorId(actorId).stream().map(filmEntity -> Film.builder()
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
                .build()).collect(Collectors.toList());
    }

    protected FilmEntity queryFilm(Film film) throws UnknownFilmException {
        Optional<FilmEntity> filmEntity =
                filmRepository.findById(film.getId());
        if (filmEntity.isEmpty()) {
            throw new UnknownFilmException(String.format("Unknown film id: %s", film.getId()));
        }
        log.info("Film Entity: {}", filmEntity.get());
        return filmEntity.get();
    }
    protected Actor convertActorEntityToActor(ActorEntity actorEntity) {
        return Actor.builder()
                .id(actorEntity.getId())
                .firstName(actorEntity.getFirstName())
                .lastName(actorEntity.getLastName()).build();
    }

    protected ActorEntity convertActorToActorEntity(Actor actor) {
        return ActorEntity.builder()
                .id(actor.getId())
                .firstName(actor.getFirstName())
                .lastName(actor.getLastName())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
    }
}
