package bookstore.service.order;

import bookstore.dto.order.CreateOrderRequestDto;
import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderItemDto;
import bookstore.dto.order.UpdateOrderDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    List<OrderDto> getAllOrders(String email, Pageable pageable);

    OrderDto setOrderStatus(Long id, UpdateOrderDto updateOrderDto);

    OrderDto saveOrder(String email, CreateOrderRequestDto requestDto);

    List<OrderItemDto> getItems(Long id);

    OrderItemDto getById(Long orderId, Long itemId);
}
