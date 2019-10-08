package pl.javagda25.spring.la_biblioteca.model;


import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private int yearWritten;

    @Formula("year(now() - year_written)")
    private Integer howOld;

    private int numberOfPages;
    private int numberOfAvailableCopies;

    @Formula("(select count(*) from book_lent bl where (bl.book_id = id) and (bl.date_returned is null))")
    private int numberOfBorrowedCopies;

    @ManyToOne(fetch = FetchType.LAZY)
    private PublishingHouse publishingHouse;

    public Book(String title, int yearWritten, int numberOfPages, int numberOfAvailableCopies) {
        this.title = title;
        this.yearWritten = yearWritten;
        this.numberOfPages = numberOfPages;
        this.numberOfAvailableCopies = numberOfAvailableCopies;
    }

//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
//    private Set<Author> authors = new HashSet<>();
//
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private List<BookLent> currentLents;

}
