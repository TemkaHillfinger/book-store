package bookstore.service.order;

import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderItemDto;
import bookstore.model.Order;
import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders(String email);

    OrderDto setOrderStatus(Long id, Order.Status status);

    OrderDto saveOrder(String email, String shippingAddress);

    List<OrderItemDto> getItems(Long id);

    OrderItemDto getById(Long orderId, Long itemId);
}
