package hu.unideb.webdev.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActorFilmDto {

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
    private String rating;
}
