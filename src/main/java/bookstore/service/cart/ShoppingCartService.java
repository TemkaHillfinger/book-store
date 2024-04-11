package bookstore.service.cart;

import bookstore.dto.cart.AddToCartRequest;
import bookstore.dto.cart.ShoppingCartDto;
import bookstore.dto.cartitem.UpdateCartItemRequest;
import bookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartDto findUserShoppingCart(Long userId);

    ShoppingCartDto addBookToCart(AddToCartRequest request, User user);

    ShoppingCartDto updateCartItemQuantity(Long cartItemId, UpdateCartItemRequest request);

    void deleteItemFromCart(Long cartItemId);
}
