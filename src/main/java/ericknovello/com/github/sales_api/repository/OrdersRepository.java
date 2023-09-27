package ericknovello.com.github.sales_api.repository;

import ericknovello.com.github.sales_api.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Query("select p from Orders p left join fetch p.items where p.id = :id")
    Optional<Orders> findByIdFetchItems(@Param("id") Integer id);

}
