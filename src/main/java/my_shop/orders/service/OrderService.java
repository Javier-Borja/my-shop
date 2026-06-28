package my_shop.orders.service;

import jakarta.transaction.Transactional;
import my_shop.common.exceptions.ResourceNotFoundException;
import my_shop.orders.dto.OrderResponseDto;
import my_shop.orders.enums.OrderStatus;
import my_shop.orders.mapper.OrderMapper;
import my_shop.orders.model.Order;
import my_shop.orders.repository.OrderRepository;
import my_shop.users.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderResponseDto getOrderSummary(User user, String sessionId) {

        Order order = orderRepository.findByStripeCheckoutSessionIdAndUser(sessionId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada o acceso denegado"));

        return orderMapper.toOrderResponse(order);
    }

    @Transactional
    public List<OrderResponseDto> getMyOrders(User user) {
        List<Order> orders = orderRepository.findAllByUserAndStatusOrderByCreatedAtDesc(
                user,
                OrderStatus.PAID
        );

        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Transactional
    public void markAsPaid(String externalId) {
        UUID uuid = UUID.fromString(externalId);

        Order order = orderRepository.findByExternalId(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada"));

        if (!"PAID".equals(order.getStatus())) {
            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);
        }
    }
}
