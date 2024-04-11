package bookstore.repository.cart;

import bookstore.model.ShoppingCart;
import bookstore.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByUserId(Long userId);

    Optional<ShoppingCart> findByUser(User currentUser);
}
