package pl.javagda25.spring.la_biblioteca.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.javagda25.spring.la_biblioteca.model.Book;
import pl.javagda25.spring.la_biblioteca.model.BookLent;
import pl.javagda25.spring.la_biblioteca.model.Client;
import pl.javagda25.spring.la_biblioteca.repository.BookLentRepository;
import pl.javagda25.spring.la_biblioteca.repository.BookRepository;
import pl.javagda25.spring.la_biblioteca.repository.ClientRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;


@AllArgsConstructor
@Service
public class BookLentService {
    private final BookLentRepository bookLentRepository;
    private final ClientRepository clientRepository;
    private final BookRepository bookRepository;

    public Optional<BookLent> getById(Long id) {

        return bookLentRepository.findById(id);
    }

    public void saveLent(BookLent bookLent, Long clientId, Long bookId) {
        if (clientRepository.existsById(clientId)) {
            Client client = clientRepository.getOne(clientId);
            bookLent.setClient(client);
        } else {
            throw new EntityNotFoundException("Client not found!!!!");
        }
        if (bookRepository.existsById(bookId)) {
            Book book = bookRepository.getOne(bookId);
            bookLent.setBook(book);
        } else {
            throw new EntityNotFoundException("Book not found!!!!");
        }
        bookLent.setDateLent(LocalDate.now());
        bookLentRepository.save(bookLent);
    }

    public void update(BookLent bookLent) {
        bookLentRepository.save(bookLent);
    }
}
