package hu.unideb.webdev.service;

import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.Model.Film;
import hu.unideb.webdev.exceptions.UnknownActorException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.repository.dao.ActorDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ActorServiceImpl implements ActorService {

    private final ActorDao actorDao;


    @Override
    public Collection<Actor> getAllActors() {
        return actorDao.readAll();
    }

    @Override
    public Collection<Actor> getActorsInFilm(int filmId) {
        return actorDao.getActorsByFilmId(filmId);
    }

    @Override
    public void recordActor(Actor actor) throws UnknownFilmException {
        actorDao.createActor(actor);
    }

    @Override
    public void deleteActor(Actor actor) throws UnknownActorException {
        actorDao.deleteActor(actor);
    }

    @Override
    public void updateActor(Actor actor) throws UnknownActorException, UnknownFilmException {
        actorDao.updateActor(actor);
    }

    @Override
    public Actor getActorById(int actorId) throws UnknownActorException {
        return actorDao.getActorById(actorId);
    }

    @Override
    public List<Actor> getActorsByName(String name) throws UnknownActorException {
        return actorDao.getActorsByName(name);
    }
}
