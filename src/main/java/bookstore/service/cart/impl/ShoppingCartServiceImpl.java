package bookstore.service.cart.impl;

import bookstore.dto.cart.AddToCartRequest;
import bookstore.dto.cart.ShoppingCartDto;
import bookstore.dto.cartitem.UpdateCartItemRequest;
import bookstore.exeption.EntityNotFoundException;
import bookstore.mapper.BookMapper;
import bookstore.mapper.ShoppingCartMapper;
import bookstore.model.Book;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import bookstore.model.User;
import bookstore.repository.cart.ShoppingCartRepository;
import bookstore.repository.cartitem.CartItemRepository;
import bookstore.service.cart.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final BookMapper bookMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartDto findUserShoppingCart(Long userid) {
        return shoppingCartMapper.toDto(shoppingCartRepository.findByUserId(userid));
    }

    @Override
    @Transactional
    public ShoppingCartDto addBookToCart(AddToCartRequest request, User user) {
        Book book = bookMapper.bookFromId(request.getBookId());
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user)
                .orElseGet(() -> {
                    ShoppingCart newShoppingCart = new ShoppingCart();
                    newShoppingCart.setUser(user);
                    return shoppingCartRepository.save(newShoppingCart);
                });
        CartItem existingCartItem = cartItemRepository
                .findByShoppingCartAndBook(shoppingCart, book);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setShoppingCart(shoppingCart);
            newCartItem.setBook(book);
            newCartItem.setQuantity(request.getQuantity());
            cartItemRepository.save(newCartItem);
            shoppingCart.getCartItems().add(newCartItem);
        }
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateCartItemQuantity(Long cartItemId, UpdateCartItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart item with id: " + cartItemId));
        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        ShoppingCart shoppingCart = cartItem.getShoppingCart();
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public void deleteItemFromCart(Long cartItemId) {
        shoppingCartRepository.deleteById(cartItemId);
    }
}
