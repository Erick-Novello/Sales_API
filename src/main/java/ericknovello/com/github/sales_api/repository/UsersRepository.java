package ericknovello.com.github.sales_api.repository;

import ericknovello.com.github.sales_api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByLogin(String login);

}
