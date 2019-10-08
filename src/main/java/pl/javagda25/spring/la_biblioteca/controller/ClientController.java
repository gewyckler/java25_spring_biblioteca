package pl.javagda25.spring.la_biblioteca.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.javagda25.spring.la_biblioteca.model.Client;
import pl.javagda25.spring.la_biblioteca.service.ClientService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/client/")
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/add")
    public String add(Model model, Client client) {
        model.addAttribute("client", client);
        return "client-add";
    }

    @PostMapping("/add")
    public String add(Client client) {
        clientService.save(client);
        return "redirect:/client/list";
    }

    @GetMapping("/list")
    public String listClient(Model model) {
        List<Client> clientList = clientService.getAll();
        model.addAttribute("clientList", clientList);
        return "client-list";
    }

    @GetMapping("/remove/{clientId}")
    public String removeClientById(HttpServletRequest request,
                                   @PathVariable(name = "clientId") Long clientId) {
        clientService.removeById(clientId);
        String referer = request.getHeader("referer");
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "redirect:/client/list";
    }

    @GetMapping("edit/{clientId}")
    public String editClient(Model model,
                             @PathVariable(name = "clientId") Long clientId) {
        Optional<Client> clientOptional = clientService.getById(clientId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            model.addAttribute("client", client);
            return "client-add";
        }
        return "redirect:/client/list";
    }

    @GetMapping("/details/{clientId}")
    public String getDetails(Model model, HttpServletRequest request,
                             @PathVariable(name = "clientId") Long clientId) {
        Optional<Client> optionalClient = clientService.getById(clientId);
        String referer = request.getHeader("referer");
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            model.addAttribute("lents", client.getLents());
            model.addAttribute("client", client);
            model.addAttribute("referer", referer);
            return "client-details";
        }
        return "redirect:/client/list";
    }
}
