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

    private static final long serialVersionUID = 1L;

    private int actorId;
    private int filmId;
}
