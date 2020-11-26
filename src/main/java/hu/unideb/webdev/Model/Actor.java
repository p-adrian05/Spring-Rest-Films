package hu.unideb.webdev.Model;

import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Actor {


    private int id;

    private String firstName;

    private String lastName;

    private List<Film> films;
}
