package pl.javagda25.spring.la_biblioteca.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PublishingHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "publishingHouse", fetch = FetchType.EAGER)
    private Set<Book> bookSet;

    public PublishingHouse(String name) {
        this.name = name;
    }
}
