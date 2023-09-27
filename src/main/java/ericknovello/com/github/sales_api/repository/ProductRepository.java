package ericknovello.com.github.sales_api.repository;

import ericknovello.com.github.sales_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
