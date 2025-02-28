package hu.unideb.webdev.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Pattern;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String name;

    private int filmCount;
}
