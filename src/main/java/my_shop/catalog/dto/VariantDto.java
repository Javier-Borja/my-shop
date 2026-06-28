package my_shop.catalog.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class VariantDto {

    private UUID id;
    private String sku;
    private BigDecimal price;
    private Integer stockQuantity;
    private String size;
    private String color;

    public VariantDto() {
    }

    public VariantDto(UUID id, String sku, BigDecimal price,
                      Integer stockQuantity, String size, String color) {
        this.id = id;
        this.sku = sku;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.size = size;
        this.color = color;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
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
}
