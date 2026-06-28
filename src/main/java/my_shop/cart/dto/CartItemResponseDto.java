package my_shop.cart.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class CartItemResponseDto {

    private UUID variantId;
    private String productName;
    private String sku;
    private String size;
    private String color;
    private String imageUrl;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;

    public CartItemResponseDto() {
    }

    public CartItemResponseDto(UUID variantId, String productName, String sku, String size,
                               String color, String imageUrl, Integer quantity, BigDecimal price,
                               BigDecimal subtotal) {
        this.variantId = variantId;
        this.productName = productName;
        this.sku = sku;
        this.size = size;
        this.color = color;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    public UUID getVariantId() {
        return variantId;
    }

    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
