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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;
    private ShoppingCartMapper shoppingCartMapper;
    private BookMapper bookMapper;
    private CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartDto findUserShoppingCart(User user, Pageable pageable) {
        Page<ShoppingCart> page = shoppingCartRepository.findByUserId(user.getId(), pageable);
        ShoppingCart shoppingCart = page.getContent().get(0);
        ShoppingCartDto dto = shoppingCartMapper.toDto(shoppingCart);
        dto.setCartItems(shoppingCartMapper.mapCartItemsDto(shoppingCart.getCartItems()));
        return dto;
    }

    @Override
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
    public ShoppingCartDto updateCartItemQuantity(Long cartItemId, UpdateCartItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart item with id: " + cartItemId));
        Book book = cartItem.getBook();
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
