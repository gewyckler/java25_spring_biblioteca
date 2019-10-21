package pl.javagda25.spring.la_biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.javagda25.spring.la_biblioteca.model.Author;
import pl.javagda25.spring.la_biblioteca.model.Book;
import pl.javagda25.spring.la_biblioteca.service.AuthorService;
import pl.javagda25.spring.la_biblioteca.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/author/")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @GetMapping("/add")
    public String getAuhor(Model model, Author author) {
        model.addAttribute("author", author);
        return "author-add";
    }

    @PostMapping("/add")
    public String getAuthor(Author author) {
        authorService.save(author);
        return "redirect:/author/list";
    }

    @GetMapping("/list")
    public String listAuthor(Model model,
                             @RequestParam(name = "page", defaultValue = "0") int page,
                             @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Author> authorList = authorService.get(PageRequest.of(page, size));
        model.addAttribute("authorList", authorList);
        return "author-list";
    }

    @GetMapping("/remove/{authorId}")
    public String removeAuthor(HttpServletRequest request,
                               @PathVariable(name = "authorId") Long authorId) {
        authorService.removeById(authorId);
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/books/{id}")
    public String addAuthorsToBooks(Model model,
                                    @PathVariable(name = "id") Long id) {
        Optional<Author> optionalAuthor = authorService.getById(id);
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();

            List<Book> books = bookService.findAll();

            model.addAttribute("author", author);
            model.addAttribute("books", books);
            return "author-bookform";
        }
        return "redirect:/author/list";
    }

    @PostMapping("/addBook")
    public String addBookToAuthor(Long authorId, Long bookId, HttpServletRequest request) {
        authorService.addBookToAuthor(authorId, bookId);

        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/book/remove/{bookId}/{authorId}")
    public String removeBookFromAuthor(HttpServletRequest request,
                                       @PathVariable(name = "bookId") Long bookId,
                                       @PathVariable(name = "authorId") Long authorId) {
        authorService.removeBook(authorId, bookId);

        return "redirect:" + request.getHeader("referer");
    }
}
