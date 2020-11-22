package hu.unideb.webdev.repository.util;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Embeddable
public class FilmCategoryId implements Serializable {

    private static final long serialVersionUID = 1L;

    private int categoryId;
    private int filmId;
}
