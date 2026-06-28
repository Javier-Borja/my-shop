package my_shop.orders.controller;

import my_shop.orders.dto.OrderResponseDto;
import my_shop.orders.service.OrderService;
import my_shop.users.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/summary/{sessionId}")
    public ResponseEntity<OrderResponseDto> getOrderSummary(
            @AuthenticationPrincipal User user,
            @PathVariable String sessionId) {
        OrderResponseDto response = orderService.getOrderSummary(user, sessionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponseDto>> getMyOrders(@AuthenticationPrincipal User user) {
        List<OrderResponseDto> orders = orderService.getMyOrders(user);
        return ResponseEntity.ok(orders);
    }
}
