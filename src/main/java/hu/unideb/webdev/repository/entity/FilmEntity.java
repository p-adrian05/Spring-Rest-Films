package hu.unideb.webdev.repository.entity;


import hu.unideb.webdev.repository.util.Rate;
import hu.unideb.webdev.repository.util.RateConverter;
import hu.unideb.webdev.repository.util.SpecialFeature;
import hu.unideb.webdev.repository.util.SpecialFeatureConverter;
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
    @Convert(converter = SpecialFeatureConverter.class)
    private List<SpecialFeature> specialFeatures;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
}
