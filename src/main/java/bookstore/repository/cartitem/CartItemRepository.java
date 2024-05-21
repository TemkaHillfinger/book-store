package bookstore.repository.cartitem;

import bookstore.model.Book;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByShoppingCartAndBook(ShoppingCart shoppingCart, Book book);
}
