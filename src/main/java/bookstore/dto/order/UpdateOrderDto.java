package bookstore.dto.order;

import bookstore.model.Order;

public record UpdateOrderDto(Order.Status status) {
}
