package hu.unideb.webdev.controller.dto;

import hu.unideb.webdev.repository.util.Rate;
import hu.unideb.webdev.repository.util.SpecialFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FilmDto {

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

    private String rating;

    private List<String> specialFeatures;

    private List<String> categories;
}
