package pl.javagda25.spring.la_biblioteca.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.javagda25.spring.la_biblioteca.model.PublishingHouse;
import pl.javagda25.spring.la_biblioteca.repository.PublishingHouseRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PublishingHouseService {
    private final PublishingHouseRepository publishingHouseRepository;


    public void add(PublishingHouse publishingHouse) {
        publishingHouseRepository.save(publishingHouse);
    }

    public List<PublishingHouse> findAll() {
        return publishingHouseRepository.findAll();
    }

    public void deleteById(Long id) {
        publishingHouseRepository.deleteById(id);
    }

    public Optional<PublishingHouse> getById(Long id) {
        return publishingHouseRepository.findById(id);
    }
}
