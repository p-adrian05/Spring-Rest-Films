package hu.unideb.webdev.Model;
import hu.unideb.webdev.repository.util.Rate;
import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
@Builder
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

    private Set<String> specialFeatures;

    private List<String> categories;
}
