package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.cart.ShoppingCartDto;
import bookstore.dto.cartitem.CartItemDto;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cartItems", target = "cartItems")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    default Set<CartItemDto> mapCartItemsDto(Set<CartItem> cartItems) {
        if (cartItems == null) {
            return null;
        }
        return cartItems.stream()
                .map(cartItem -> {
                    CartItemDto cartItemDto = new CartItemDto();
                    cartItemDto.setId(cartItem.getId());
                    cartItemDto.setBookId(cartItem.getBook().getId());
                    cartItemDto.setBookTitle(cartItem.getBook().getTitle());
                    cartItemDto.setQuantity(cartItem.getQuantity());
                    return cartItemDto;
                })
                .collect(Collectors.toSet());
    }
}
