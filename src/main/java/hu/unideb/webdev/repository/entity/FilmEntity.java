package hu.unideb.webdev.repository.entity;


import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    @Column(name = "rental_duration")
    private int rentalDuration;

    @Column(name = "rental_rate")
    private double rentalRate;

    @Column
    private int length;

    @Column(name = "replacement_cost")
    private double replacementCost;

    @Column
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Column(name = "special_features")
    @ElementCollection
    private Set<String> specialFeatures;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
}
