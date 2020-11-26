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

    private List<String> categories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id &&
                releaseYear == film.releaseYear &&
                rentalDuration == film.rentalDuration &&
                Double.compare(film.rentalRate, rentalRate) == 0 &&
                length == film.length &&
                Double.compare(film.replacementCost, replacementCost) == 0 &&
                Objects.equals(title, film.title) &&
                Objects.equals(description, film.description) &&
                Objects.equals(language, film.language) &&
                Objects.equals(originalLanguage, film.originalLanguage) &&
                rating == film.rating &&
                Objects.equals(specialFeatures, film.specialFeatures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, releaseYear, language, originalLanguage, rentalDuration, rentalRate, length, replacementCost, rating, specialFeatures);
    }
}
