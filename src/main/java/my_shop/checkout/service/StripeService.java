package my_shop.checkout.service;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import my_shop.catalog.model.ProductImage;
import my_shop.common.exceptions.InternalServerException;
import my_shop.common.exceptions.PaymentProcessingException;
import my_shop.orders.model.Order;
import my_shop.orders.model.OrderItem;
import my_shop.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StripeService {

    private final OrderService orderService;

    @Value("${stripe.api.key}")
    private String secretKey;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    public StripeService(OrderService orderService) {
        this.orderService = orderService;
    }

    public String createCheckoutSession(Order order) {
        Stripe.apiKey = secretKey;

        try {
            List<SessionCreateParams.LineItem> stripeItems = buildLineItems(order);

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(frontendUrl + "/#/payment-success?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl(frontendUrl + "/#/payment-cancel")
                    .setCustomerEmail(order.getUser().getEmail())
                    .addAllLineItem(stripeItems)
                    .putMetadata("order_id", order.getExternalId().toString())
                    .build();

            Session session = Session.create(params);
            order.setStripeCheckoutSessionId(session.getId());
            return session.getUrl();

        } catch (StripeException e) {
            throw new PaymentProcessingException("Error al conectar con la pasarela de pagos: " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerException("Error interno al procesar el checkout");
        }
    }

    private List<SessionCreateParams.LineItem> buildLineItems(Order order) {
        return order.getItems().stream()
                .map(item -> SessionCreateParams.LineItem.builder()
                        .setQuantity((long) item.getQuantity())
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(item.getPriceAtPurchase()
                                        .multiply(new BigDecimal(100))
                                        .longValue())
                                .setProductData(buildProductData(item))
                                .build())
                        .build())
                .toList();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData buildProductData(OrderItem item) {
        String description = "Color: " + item.getVariant().getColor() +
                (item.getVariant().getSize() != null ? " - Talla: " + item.getVariant().getSize() : "");

        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getVariant().getProduct().getName())
                .setDescription(description)
                .addImage(resolveImageUrl(item))
                .build();
    }

    private String resolveImageUrl(OrderItem item) {
        return item.getVariant().getProduct().getImages().stream()
                .filter(img -> img.getColor() != null && img.getColor().equalsIgnoreCase(item.getVariant().getColor()))
                .findFirst()
                .map(ProductImage::getImageUrl)
                .orElseGet(() -> item.getVariant().getProduct().getImages().stream()
                        .filter(ProductImage::getPrimary)
                        .findFirst()
                        .map(ProductImage::getImageUrl)
                        .orElse(item.getVariant().getProduct().getImages().get(0).getImageUrl()));
    }

    public void processWebhookEvent(String payload, String sigHeader) {
        Stripe.apiKey = secretKey;
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            throw new PaymentProcessingException("Firma de Stripe inválida");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            handleCompletedSession(event);
        }
    }

    private void handleCompletedSession(Event event) {
        Session session = (Session) event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> new InternalServerException("Error al deserializar la sesión de Stripe"));

        String orderExternalId = session.getMetadata().get("order_id");

        if (orderExternalId != null) {
            orderService.markAsPaid(orderExternalId);
        }
    }
}
