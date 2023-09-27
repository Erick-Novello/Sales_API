package ericknovello.com.github.sales_api.repository;

import ericknovello.com.github.sales_api.model.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedItemRepository extends JpaRepository<OrderedItem, Integer> {

}
