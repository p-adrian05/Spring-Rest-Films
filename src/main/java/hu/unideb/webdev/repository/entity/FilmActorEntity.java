package hu.unideb.webdev.repository.entity;

import hu.unideb.webdev.repository.util.FilmActorId;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "film_actor", schema = "sakila")
public class FilmActorEntity {

    @EmbeddedId
    private FilmActorId id;

    @ManyToOne
    @MapsId("actorId")
    @JoinColumn(name = "actor_id")
    private ActorEntity actor;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("filmId")
    @JoinColumn(name = "film_id")
    private FilmEntity film;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public FilmActorEntity(ActorEntity actor, FilmEntity film, Timestamp lastUpdate) {
        this.id = new FilmActorId();
        this.actor = actor;
        this.film = film;
        this.lastUpdate = lastUpdate;
    }
}
