package pl.javagda25.spring.la_biblioteca.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.javagda25.spring.la_biblioteca.model.Client;
import pl.javagda25.spring.la_biblioteca.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;


    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    public void removeById(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    public Optional<Client> getById(Long clientId) {
        return clientRepository.findById(clientId);
    }
}
