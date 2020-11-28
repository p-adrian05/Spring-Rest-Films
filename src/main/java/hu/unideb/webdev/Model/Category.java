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

    private String name;

    private int filmCount;

}
