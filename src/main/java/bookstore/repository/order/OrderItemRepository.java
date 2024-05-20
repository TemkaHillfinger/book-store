package bookstore.repository.order;

import bookstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("FROM OrderItem oi JOIN oi.order o WHERE oi.id = :itemId")
    OrderItem getById(@Param("itemId") Long itemId);
}
