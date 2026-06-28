package my_shop.orders.repository;

import my_shop.orders.enums.OrderStatus;
import my_shop.orders.model.Order;
import my_shop.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByStripeCheckoutSessionId(String sessionId);

    Optional<Order> findByStripeCheckoutSessionIdAndUser(String sessionId, User user);

    Optional<Order> findByExternalId(UUID externalId);

    List<Order> findAllByUserAndStatusOrderByCreatedAtDesc(User user, OrderStatus status);
}
