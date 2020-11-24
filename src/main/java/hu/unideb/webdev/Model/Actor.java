package hu.unideb.webdev.Model;

import lombok.*;

import javax.persistence.Column;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Actor {

    private String firstName;

    private String lastName;

    private List<Film> films;
}
