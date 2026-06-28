package my_shop.orders.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderResponseDto {

    private UUID externalId;
    private String status;
    private BigDecimal totalAmount;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private Map<String, Object> shippingAddress;
    private List<OrderItemResponseDto> items;

    public OrderResponseDto() {
    }

    public OrderResponseDto(UUID externalId, String status, BigDecimal totalAmount, BigDecimal subtotal,
                            BigDecimal taxAmount, Map<String, Object> shippingAddress, List<OrderItemResponseDto> items) {
        this.externalId = externalId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.subtotal = subtotal;
        this.taxAmount = taxAmount;
        this.shippingAddress = shippingAddress;
        this.items = items;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Map<String, Object> getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Map<String, Object> shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderItemResponseDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponseDto> items) {
        this.items = items;
    }
}
