package my_shop.catalog.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductCardResponseDto {
    private UUID id;
    private String title;
    private String slug;
    private BigDecimal price;
    private String imageUrl;
    private String category;
    private String brand;
    private UUID productVariantId;

    public ProductCardResponseDto() {
    }

    public ProductCardResponseDto(UUID id, String title, String slug, BigDecimal price, String imageUrl, String category, String brand, UUID productVariantId) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.brand = brand;
        this.productVariantId = productVariantId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public UUID getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(UUID productVariantId) {
        this.productVariantId = productVariantId;
    }
}
