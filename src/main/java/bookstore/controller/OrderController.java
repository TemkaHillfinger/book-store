package bookstore.controller;

import bookstore.dto.order.CreateOrderRequestDto;
import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderItemDto;
import bookstore.dto.order.UpdateOrderDto;
import bookstore.service.order.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private OrderService orderService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public OrderDto updateStatus(@PathVariable Long id,
                                 @RequestBody UpdateOrderDto updateOrderDto) {
        return orderService.setOrderStatus(id, updateOrderDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public OrderDto placeAnOrder(@RequestBody CreateOrderRequestDto placeOrderDto,
                                 Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return orderService.saveOrder(userDetails.getUsername(), placeOrderDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<OrderDto> getAllOrders(Authentication authentication,
                                       @ParameterObject @PageableDefault Pageable pageable) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return orderService.getAllOrders(userDetails.getUsername(),pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items")
    public List<OrderItemDto> getItemsFromOrder(@PathVariable Long orderId) {
        return orderService.getItems(orderId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderDto getOneItemFromOrder(@PathVariable Long itemId) {
        return orderService.getById(itemId);
    }
}
