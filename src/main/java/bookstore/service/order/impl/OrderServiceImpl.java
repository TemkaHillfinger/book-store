package bookstore.service.order.impl;

import bookstore.dto.order.CreateOrderRequestDto;
import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderItemDto;
import bookstore.dto.order.UpdateOrderDto;
import bookstore.exeption.EntityNotFoundException;
import bookstore.mapper.OrderItemMapper;
import bookstore.mapper.OrderMapper;
import bookstore.model.CartItem;
import bookstore.model.Order;
import bookstore.model.OrderItem;
import bookstore.model.ShoppingCart;
import bookstore.repository.cart.ShoppingCartRepository;
import bookstore.repository.order.OrderRepository;
import bookstore.service.order.OrderService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderDto> getAllOrders(String email, Pageable pageable) {
        return orderMapper.toDtos(orderRepository.findAllByUserEmail(email, pageable));
    }

    @Transactional
    @Override
    public OrderDto setOrderStatus(Long id, UpdateOrderDto updateOrderDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can not find order by id: " + id));

        order.setStatus(updateOrderDto.status());
        return orderMapper.toDto(orderRepository.save(order));

    }

    @Transactional
    @Override
    public OrderDto saveOrder(String email, CreateOrderRequestDto requestDto) {
        ShoppingCart userShoppingCart = shoppingCartRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can not find user's shopping cart. User email: " + email));

        Order userOrder = new Order(userShoppingCart);
        userOrder.setShippingAddress(requestDto.shippingAddress());
        BigDecimal total = BigDecimal.ZERO;
        Set<OrderItem> orderItems = new HashSet<>();

        for (CartItem item : userShoppingCart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(item.getBook());
            orderItem.setPrice(item.getBook().getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrder(userOrder);
            orderItems.add(orderItem);
            total = total.add(item.getBook().getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        userOrder.setTotal(total);
        userOrder.setOrderItems(orderItems);
        userShoppingCart.clearCartItems();

        return orderMapper.toDto(orderRepository.save(userOrder));
    }

    @Override
    public List<OrderItemDto> getItems(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Can not find order by id:" + id));
        return orderItemMapper.toDtos(order.getOrderItems());
    }

    @Override
    public OrderDto getById(Long itemId) {
        Order order = orderRepository.findById(itemId).orElseThrow(
                () -> new EntityNotFoundException("Can`t find order by id " + itemId));
        return orderMapper.toDto(order);
    }
}
