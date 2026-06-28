package my_shop.orders.model;

import jakarta.persistence.*;
import my_shop.catalog.model.ProductVariant;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "price_at_purchase", nullable = false, precision = 12, scale = 2)
    private BigDecimal priceAtPurchase;

    @Column(name = "subtotal_at_purchase", nullable = false)
    private BigDecimal subtotalAtPurchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    public OrderItem() {
    }

    public OrderItem(Long id, Integer quantity, BigDecimal priceAtPurchase, BigDecimal subtotalAtPurchase,
                     Order order, ProductVariant variant) {
        this.id = id;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
        this.subtotalAtPurchase = subtotalAtPurchase;
        this.order = order;
        this.variant = variant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public BigDecimal getSubtotalAtPurchase() {
        return subtotalAtPurchase;
    }

    public void setSubtotalAtPurchase(BigDecimal subtotalAtPurchase) {
        this.subtotalAtPurchase = subtotalAtPurchase;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductVariant getVariant() {
        return variant;
    }

    public void setVariant(ProductVariant variant) {
        this.variant = variant;
    }
}
