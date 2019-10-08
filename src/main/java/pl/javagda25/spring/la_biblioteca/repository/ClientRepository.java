package pl.javagda25.spring.la_biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.javagda25.spring.la_biblioteca.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
