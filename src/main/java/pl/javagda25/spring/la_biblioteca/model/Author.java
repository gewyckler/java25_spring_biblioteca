package pl.javagda25.spring.la_biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
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

    private LocalDate birthTime;

    // z powodu relacji many to many mamy relacje posredniczaco o nazwie Author_Book/
    // zapytanie musi dotyczyc tebeli posredniczacej i zliczac wystapienie w tej tabeli
    @Formula("(select count(*) from author_book ab where ab.authors_id = id)")
    private int numberOfBooks;

    // możemy z tej strony dodawać (książki do autorów) żeby tworzyć relacje
//    @EqualsAndHashCode.Exclude
//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<Book> books = new HashSet<>();
}
