package my_shop.catalog.dto;

import java.util.List;
import java.util.UUID;

public class ProductDetailResponseDto {

    private UUID id;
    private String name;
    private String slug;
    private String description;
    private BrandDto brand;
    private CategoryDto category;
    private List<VariantDto> variants;
    private List<ImageDto> images;

    public ProductDetailResponseDto() {
    }

    public ProductDetailResponseDto(UUID id, String name, String slug, String description,
                                    BrandDto brand, CategoryDto category,
                                    List<VariantDto> variants, List<ImageDto> images) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.variants = variants;
        this.images = images;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BrandDto getBrand() {
        return brand;
    }

    public void setBrand(BrandDto brand) {
        this.brand = brand;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public List<VariantDto> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantDto> variants) {
        this.variants = variants;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
    }
}
