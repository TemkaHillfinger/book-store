package bookstore.repository.cart;

import bookstore.model.ShoppingCart;
import bookstore.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Page<ShoppingCart> findByUserId(Long userId, Pageable pageable);

    Optional<ShoppingCart> findByUser(User currentUser);
}
