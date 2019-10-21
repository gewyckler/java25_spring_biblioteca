package pl.javagda25.spring.la_biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.javagda25.spring.la_biblioteca.model.Author;
import pl.javagda25.spring.la_biblioteca.model.Book;
import pl.javagda25.spring.la_biblioteca.repository.AuthorRepository;
import pl.javagda25.spring.la_biblioteca.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Page<Author> get(PageRequest pageRequest) {
        return authorRepository.findAll(pageRequest);
    }

    public void save(Author author) {
        authorRepository.save(author);
    }

    public Optional<Author> getById(Long id) {
        return authorRepository.findById(id);
    }

    public void addBookToAuthor(Long authorId, Long bookId) {
        if (checkIsThereABookAndAuthor(authorId, bookId)) {
            return;
        }

        Book book = bookRepository.getOne(bookId);
        Author author = authorRepository.getOne(authorId);

        author.getBooks().add(book);
        authorRepository.save(author);
    }

    public void removeBook(Long authorId, Long bookId) {
        if (checkIsThereABookAndAuthor(authorId, bookId)) {
            return;
        }

        Author author = authorRepository.getOne(authorId);
        Book book = bookRepository.getOne(bookId);

        author.getBooks().remove(book);
        authorRepository.save(author);
    }

    private boolean checkIsThereABookAndAuthor(Long authorId, Long bookId) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (!optionalAuthor.isPresent()) {
            return true;
        }
        if (!optionalBook.isPresent()) {
            return true;
        }
        return false;
    }

    public void removeById(Long authorId) {
        authorRepository.deleteById(authorId);
    }
}
