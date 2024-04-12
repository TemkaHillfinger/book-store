package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.cart.ShoppingCartDto;
import bookstore.dto.cartitem.CartItemDto;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    Set<CartItemDto> mapCartItemsDto(Set<CartItem> cartItems);
}
