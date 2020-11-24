package hu.unideb.webdev.repository.entity;

import hu.unideb.webdev.repository.util.FilmActorId;
import hu.unideb.webdev.repository.util.FilmCategoryId;
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
@Table(name = "film_category", schema = "sakila")
public class FilmCategoryEntity {

    @EmbeddedId
    private FilmCategoryId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("filmId")
    @JoinColumn(name = "film_id")
    private FilmEntity film;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private CategoryEntity category;


    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public FilmCategoryEntity(CategoryEntity category, FilmEntity film, Timestamp lastUpdate) {
        this.id = new FilmCategoryId();
        this.category = category;
        this.film = film;
        this.lastUpdate = lastUpdate;
    }
}
