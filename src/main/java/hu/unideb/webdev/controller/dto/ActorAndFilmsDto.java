package hu.unideb.webdev.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
@SuperBuilder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ActorAndFilmsDto extends ActorDto {

    public Collection<ActorFilmDto> films;
}
