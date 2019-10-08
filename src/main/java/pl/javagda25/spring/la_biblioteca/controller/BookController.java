package pl.javagda25.spring.la_biblioteca.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.javagda25.spring.la_biblioteca.model.Book;
import pl.javagda25.spring.la_biblioteca.service.BookService;
import pl.javagda25.spring.la_biblioteca.service.ClientService;
import pl.javagda25.spring.la_biblioteca.service.PublishingHouseService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/book/")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;
    private final PublishingHouseService publishingHouseService;

    @GetMapping("/add")
    public String add(Model model, Book book) {

        model.addAttribute("publishingHouses", publishingHouseService.findAll());
        model.addAttribute("book", book);
        return "book-add";
    }

    @PostMapping("/add")
    public String add(Long publishingHouseId, Book book) {
        book.setYearWritten(LocalDateTime.now().getYear());
        bookService.saveBook(book, publishingHouseId);
        return "redirect:/pb/list";
    }

    @GetMapping("/details/{bookId}")
    public String details(Model model, HttpServletRequest request,
                          @PathVariable(name = "bookId") Long bookId) {
        Optional<Book> optionalBook = bookService.getById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            model.addAttribute("book", book);
            model.addAttribute("referer", request.getHeader("referer"));
            return "book-details";
        }
        return "redirect:/book/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);
        return "book-list";
    }

    @GetMapping("/edit/{id}")
    public String getForm(Model model, @PathVariable(name = "id") Long id) {
        Optional<Book> optionalBook = bookService.getById(id);

        if (optionalBook.isPresent()) {
            model.addAttribute("publishingHouses", publishingHouseService.findAll());
            model.addAttribute("book", optionalBook.get());

            return "book-add";
        }

        return "redirect:/book/list";
    }

    @GetMapping("/remove/{bookId}")
    public String remove(HttpServletRequest request,
                         @PathVariable(name = "bookId") Long bookId) {
        String referer = request.getHeader("referer");
        bookService.deleteById(bookId);
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "redirect:/book/list";
    }
}
