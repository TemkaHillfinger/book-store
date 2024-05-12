package bookstore.service.order.impl;

import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderItemDto;
import bookstore.exeption.EntityNotFoundException;
import bookstore.mapper.OrderItemMapper;
import bookstore.mapper.OrderMapper;
import bookstore.model.CartItem;
import bookstore.model.Order;
import bookstore.model.OrderItem;
import bookstore.model.ShoppingCart;
import bookstore.repository.cart.ShoppingCartRepository;
import bookstore.repository.order.OrderItemRepository;
import bookstore.repository.order.OrderRepository;
import bookstore.service.order.OrderService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;

    @Override
    public List<OrderDto> getAllOrders(String email) {
        return orderMapper.toDtos(orderRepository.findAllByUserEmail(email));
    }

    @Transactional
    @Override
    public OrderDto setOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can not find order by id:" + id));

        order.setStatus(Order.Status.valueOf(status));
        return orderMapper.toDto(orderRepository.save(order));

    }

    @Transactional
    @Override
    public OrderDto saveOrder(String email, String shippingAddress) {
        ShoppingCart userShoppingCart = shoppingCartRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can not find user's shopping cart. User email: " + email));

        Order userOrder = new Order(userShoppingCart);
        userOrder.setShippingAddress(shippingAddress);
        userOrder.setTotal(userShoppingCart.getCartItems().stream()
                .map(i -> i.getBook().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        Set<OrderItem> orderItems = new HashSet<>();

        for (CartItem item : userShoppingCart.getCartItems()) {
            OrderItem orderItem = new OrderItem(item);
            orderItems.add(orderItem);
        }
        userOrder.setOrderItems(orderItems);
        return orderMapper.toDto(orderRepository.save(userOrder));
    }

    @Override
    public List<OrderItemDto> getItems(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Can not find order by id:" + id));

        return orderItemMapper.toDtos(order.getOrderItems());
    }

    @Override
    public OrderItemDto getById(Long orderId, Long itemId) {
        return orderItemMapper.toDto(orderItemRepository.getById(orderId, itemId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can not find order by id:" + itemId)));
    }
}
