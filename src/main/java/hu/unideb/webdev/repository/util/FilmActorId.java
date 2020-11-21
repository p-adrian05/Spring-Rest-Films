package hu.unideb.webdev.repository.util;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Embeddable
public class FilmActorId implements Serializable {

    @Column(name = "actor_id")
    private int actorId;
    @Column(name = "film_id")
    private int filmId;
}
