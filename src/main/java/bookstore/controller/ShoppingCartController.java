package bookstore.controller;

import bookstore.dto.cart.AddToCartRequest;
import bookstore.dto.cart.ShoppingCartDto;
import bookstore.dto.cartitem.UpdateCartItemRequest;
import bookstore.model.User;
import bookstore.service.cart.ShoppingCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping()
    public ShoppingCartDto getShoppingCart(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.findUserShoppingCart(user, pageable);
    }

    @PostMapping
    public ShoppingCartDto addBookToShoppingCart(@RequestBody @Valid AddToCartRequest request,
                                                 Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addBookToCart(request, user);
    }

    @PutMapping("cart-items/{cartItemId}")
    public ShoppingCartDto updateCartItemQuantity(@PathVariable Long cartItemId,
                                            @RequestBody @Valid UpdateCartItemRequest request) {
        return shoppingCartService.updateCartItemQuantity(cartItemId, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("cart-items/{cartItemId}")
    public void deleteItemFromCart(@PathVariable Long cartItemId) {
        shoppingCartService.deleteItemFromCart(cartItemId);
    }
}
