package hu.unideb.webdev.Model;

import hu.unideb.webdev.repository.util.Rate;
import hu.unideb.webdev.repository.util.SpecialFeature;
import lombok.*;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Film {

    private int id;

    private String title;

    private String description;

    private int releaseYear;

    private String language;

    private String originalLanguage;

    private int rentalDuration;

    private double rentalRate;

    private int length;

    private double replacementCost;

    private Rate rating;

    private List<SpecialFeature> specialFeatures;

    @EqualsAndHashCode.Exclude
    private List<String> categories;

}
