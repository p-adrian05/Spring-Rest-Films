package hu.unideb.webdev.repository.entity;


import hu.unideb.webdev.repository.util.Rate;
import hu.unideb.webdev.repository.util.RateConverter;
import hu.unideb.webdev.repository.util.StringCollectionConverter;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "film",schema = "sakila")
public class FilmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private int id;

    @Column
    private String title;

    @Column
    private String description;

    @Column(name = "release_year")
    private int releaseYear;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private LanguageEntity languageEntity;

    @ManyToOne
    @JoinColumn(name = "original_language_id")
    private LanguageEntity originalLanguageEntity;

    @Column(name = "rental_duration")
    private int rentalDuration;

    @Column(name = "rental_rate")
    private double rentalRate;

    @Column
    private int length;

    @Column(name = "replacement_cost")
    private double replacementCost;

    @Column
    @Convert(converter = RateConverter.class)
    private Rate rating;

    @Column(name = "special_features")
    @Convert(converter = StringCollectionConverter.class)
    private Set<String> specialFeatures;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    @OneToMany(mappedBy = "film",cascade = CascadeType.ALL)
    private Set<FilmActorEntity> actors = new HashSet<>();

    @OneToMany(mappedBy = "film",cascade = CascadeType.ALL)
    private Set<FilmCategoryEntity> categories = new HashSet<>();

    public void addActor(ActorEntity actor){
        FilmActorEntity filmActor = new FilmActorEntity(actor,this,new Timestamp((new Date()).getTime()));
        actors.add(filmActor);
    }
    public void addCategory(CategoryEntity category){
        FilmCategoryEntity filmCategoryEntity = new FilmCategoryEntity(category,this,new Timestamp((new Date()).getTime()));
        categories.add(filmCategoryEntity);
    }

    @Override
    public String toString() {
        return "FilmEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseYear=" + releaseYear +
                ", languageEntity=" + languageEntity +
                ", originalLanguageEntity=" + originalLanguageEntity +
                ", rentalDuration=" + rentalDuration +
                ", rentalRate=" + rentalRate +
                ", length=" + length +
                ", replacementCost=" + replacementCost +
                ", rating=" + rating +
                ", specialFeatures=" + specialFeatures +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, releaseYear, languageEntity, originalLanguageEntity, rentalDuration, rentalRate, length, replacementCost, rating, specialFeatures);
    }
}
