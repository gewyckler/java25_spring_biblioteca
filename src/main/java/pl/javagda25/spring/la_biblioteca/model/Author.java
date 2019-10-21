package pl.javagda25.spring.la_biblioteca.model;

import lombok.*;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    // z powodu relacji many to many mamy relacje posredniczaco o nazwie Author_Book/
    // zapytanie musi dotyczyc tebeli posredniczacej i zliczac wystapienie w tej tabeli
    @Formula("(select count(*) from author_books ab where ab.authors_id = id)")
    private Integer numberOfBooks;

    // możemy z tej strony dodawać (książki do autorów) żeby tworzyć relacje
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Book> books;
}
