package pl.javagda25.spring.la_biblioteca.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.javagda25.spring.la_biblioteca.model.Book;
import pl.javagda25.spring.la_biblioteca.model.PublishingHouse;
import pl.javagda25.spring.la_biblioteca.repository.BookRepository;
import pl.javagda25.spring.la_biblioteca.repository.PublishingHouseRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final PublishingHouseRepository publishingHouseRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public void saveBook(Book book, Long publishingHouseId) {
        if (publishingHouseRepository.existsById(publishingHouseId)) {
            PublishingHouse ph = publishingHouseRepository.getOne(publishingHouseId);
            book.setPublishingHouse(ph);

            bookRepository.save(book);
        } else {
            throw new EntityNotFoundException("Publishing house not found.");
        }
    }

    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    public void deleteById(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
