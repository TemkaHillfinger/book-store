package bookstore.controller;

import bookstore.dto.cart.AddToCartRequest;
import bookstore.dto.cart.ShoppingCartDto;
import bookstore.dto.cartitem.UpdateCartItemRequest;
import bookstore.model.User;
import bookstore.service.cart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    @Operation(summary = "Get shoppingCart", description = "Get shoppingCart")
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.findUserShoppingCart(user.getId());
    }

    @PostMapping
    @Operation(summary = "Add book", description = "Add book to the shopping cart")
    public ShoppingCartDto addBookToShoppingCart(@RequestBody @Valid AddToCartRequest request,
                                                 Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addBookToCart(request, user);
    }

    @PutMapping("cart-items/{cartItemId}")
    @Operation(summary = "Update quantity",
            description = "Update quantity of a book in the shopping cart")
    public ShoppingCartDto updateCartItemQuantity(@PathVariable Long cartItemId,
                                            @RequestBody @Valid UpdateCartItemRequest request) {
        return shoppingCartService.updateCartItemQuantity(cartItemId, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove book", description = "Remove a book from the shopping cart")
    @DeleteMapping("cart-items/{cartItemId}")
    public void deleteItemFromCart(@PathVariable Long cartItemId) {
        shoppingCartService.deleteItemFromCart(cartItemId);
    }
}
