package hu.unideb.webdev.Model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Category {

    private int id;

    private String name;

}
