package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.order.OrderItemDto;
import bookstore.model.OrderItem;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem orderItem);

    List<OrderItemDto> toDtos(Set<OrderItem> orderItems);
}
