package hu.unideb.webdev.repository.dao;

import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.repository.ActorRepository;
import hu.unideb.webdev.repository.FilmActorRepository;
import hu.unideb.webdev.repository.FilmRepository;
import hu.unideb.webdev.repository.entity.FilmEntity;
import hu.unideb.webdev.repository.util.UnknownActorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
    public int createActor(Actor actor) {
        return 0;
    }

    @Override
    public void deleteActor(Actor actor) throws UnknownActorException {

    }

    @Override
    public void updateActor(Actor actor) throws UnknownActorException {

    }

    @Override
    public Actor getActorById(int actorId) throws UnknownActorException {
        return null;
    }

    @Override
    public Collection<Actor> readAll() {
        return StreamSupport.stream(actorRepository.findAll().spliterator(),true)
                .map(entity -> Actor.builder()
                            .firstName(entity.getFirstName())
                            .lastName(entity.getLastName())
                            .films(queryFilms(entity.getId())).build())
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
}
