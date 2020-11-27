package hu.unideb.webdev.controller.dto;
import hu.unideb.webdev.repository.util.CategoryVerification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FilmDto {

    private int id;
    @NotEmpty(message = "title cannot be empty")
    private String title;
    @NotEmpty(message = "description cannot be empty")
    private String description;
    @Min(1901)
    @Max(2040)
    private int releaseYear;
    @NotEmpty(message = "language cannot be empty")
    private String language;
    @NotEmpty(message = "originalLanguage cannot be empty")
    private String originalLanguage;
    @Min(0)
    private int rentalDuration;
    @Min(0)
    private double rentalRate;
    @Min(0)
    private int length;
    @Min(0)
    private double replacementCost;

    private String rating;

    private List<String> specialFeatures;
    @CategoryVerification
    private List<String> categories;
}
