package my_shop.checkout.service;

import com.stripe.model.checkout.Session;
import jakarta.transaction.Transactional;
import my_shop.cart.model.Cart;
import my_shop.cart.model.CartItem;
import my_shop.cart.repository.CartRepository;
import my_shop.common.exceptions.ResourceNotFoundException;
import my_shop.orders.enums.OrderStatus;
import my_shop.orders.model.Order;
import my_shop.orders.model.OrderItem;
import my_shop.orders.repository.OrderRepository;
import my_shop.users.model.User;
import my_shop.users.model.UserAddress;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final StripeService stripeService;

    public CheckoutService(OrderRepository orderRepository,
                           CartRepository cartRepository,
                           StripeService stripeService) {
        this.orderRepository = orderRepository;

        this.cartRepository = cartRepository;
        this.stripeService = stripeService;
    }

    @Transactional
    public String processCheckout(User user) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un carrito para este usuario"));

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("No se puede pagar con un carrito vacío");
        }

        BigDecimal subtotal = cartItems.stream()
                .map(item -> item.getVariant().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal total = cartItems.stream()
                .map(item -> item.getVariant().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal subtotalSinIva = total.divide(new BigDecimal("1.13"), 2, RoundingMode.HALF_UP);
        BigDecimal tax = total.subtract(subtotalSinIva);

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(total);
        order.setSubtotal(subtotalSinIva);
        order.setTaxAmount(tax);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setVariant(cartItem.getVariant());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getVariant().getPrice());
            orderItem.setSubtotalAtPurchase(cartItem.getVariant().getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
            return orderItem;
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        String stripeUrl = stripeService.createCheckoutSession(savedOrder);
        orderRepository.save(savedOrder);

        return stripeUrl;
    }

    @Transactional
    public void handleSuccessfulPayment(Session session) {
        Order order = orderRepository.findByStripeCheckoutSessionId(session.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada"));

        order.setStatus(OrderStatus.PAID);
        order.setStripePaymentIntentId(session.getPaymentIntent());
        orderRepository.save(order);

        cartRepository.findByUser(order.getUser()).ifPresent(cart -> {
            cart.getItems().clear();
            cartRepository.save(cart);
        });
        
    }

    private Map<String, Object> createAddressSnapshot(UserAddress address) {
        return Map.of(
                "fullName", address.getFullName(),
                "phone", address.getPhone(),
                "department", address.getDepartment(),
                "municipality", address.getMunicipality(),
                "addressLine", address.getAddressLine()
        );
    }
}
