package pl.javagda25.spring.la_biblioteca.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.javagda25.spring.la_biblioteca.model.PublishingHouse;
import pl.javagda25.spring.la_biblioteca.service.PublishingHouseService;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/pb/")
public class PublishingHouseController {
    private final PublishingHouseService publishingHouseService;

    @GetMapping("/add")
    public String add(Model model, PublishingHouse publishingHouse) {
        model.addAttribute("pubHouse", publishingHouse);
        return "publishingHouse-add";
    }

    @PostMapping("/add")
    public String add(PublishingHouse publishingHouse) {
        publishingHouseService.add(publishingHouse);
        return "redirect:/pb/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<PublishingHouse> publishingHouseList = publishingHouseService.findAll();
        model.addAttribute("pubHouseList", publishingHouseList);
        return "publishingHouse-list";
    }

    @GetMapping("/delete/{pbId}")
    public String delete(@PathVariable(name = "pbId") Long id) {
        try {
            publishingHouseService.deleteById(id);
            return "redirect:/pb/list";
        } catch (Exception e) {
            return "redirect:/pb/list";
        }
    }

    @GetMapping("/edit/{pbId}")
    public String edit(Model model,
                       @PathVariable(name = "pbId") Long pbId) {
        Optional<PublishingHouse> optionalPublishingHouse = publishingHouseService.getById(pbId);
        if (optionalPublishingHouse.isPresent()) {
            model.addAttribute("pubHouse", optionalPublishingHouse.get());
            return "publishingHouse-add";
        } else {
            return "redirect:/pb/list";
        }
    }

    @GetMapping("/books/{id}")
    public String getPBBooks(Model model,
                             @PathVariable(name = "id") Long id) {
        Optional<PublishingHouse> publishingHouse = publishingHouseService.getById(id);
        if (publishingHouse.isPresent()) {
            PublishingHouse pb = publishingHouse.get();
            model.addAttribute("bookList", pb.getBookSet());
            return "book-list";
        }
        return "redirect:/pb/list";
    }
}
