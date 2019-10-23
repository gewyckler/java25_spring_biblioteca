package pl.javagda25.spring.la_biblioteca.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.javagda25.spring.la_biblioteca.model.BookLent;
import pl.javagda25.spring.la_biblioteca.model.Client;
import pl.javagda25.spring.la_biblioteca.service.BookLentService;
import pl.javagda25.spring.la_biblioteca.service.BookService;
import pl.javagda25.spring.la_biblioteca.service.ClientService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/booklent/")
public class BookLentController {
    private final BookLentService bookLentService;
    private final ClientService clientService;
    private final BookService bookService;

    @GetMapping("/add/{clientId}")
    public String addLentToClient(Model model, BookLent bookLent,
                                  @PathVariable(name = "clientId") Long clientId) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bookLent", bookLent);
        model.addAttribute("booksList", bookService.findAll());
        return "booklent-add";
    }

    @PostMapping("/add")
    public String addLentToClient(BookLent bookLent, Long bookId, Long clientId) {

        bookLent.setDateLent(LocalDate.now());
        bookLentService.saveLent(bookLent, clientId, bookId);

        return "redirect:/client/list";
    }

    @GetMapping("/list/{clientId}")
    public String list(Model model,
                       @PathVariable(name = "clientId") Long clientId) {
        Optional<Client> optionalClient = clientService.getById(clientId);

        if (optionalClient.isPresent()) {

            Client client = optionalClient.get();
            List<BookLent> bookLentList = client.getLents();
            model.addAttribute("booksList", bookLentList);

            return "booklent-list";
        }
        return "redirect:/client/list";
    }

    @GetMapping("/returnbook/{lentId}")
    public String returnBook(HttpServletRequest request,
                             @PathVariable(name = "lentId") Long lentId) {

        Optional<BookLent> optionalBookLent = bookLentService.getById(lentId);
        String referer = request.getHeader("referer");
        if (optionalBookLent.isPresent()) {
            BookLent bookLent = optionalBookLent.get();
            bookLent.setDateReturned(LocalDate.now());
            bookLentService.update(bookLent);
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }
}
