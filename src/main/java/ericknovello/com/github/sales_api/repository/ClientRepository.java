package ericknovello.com.github.sales_api.repository;

import ericknovello.com.github.sales_api.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
