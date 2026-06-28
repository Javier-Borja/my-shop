package my_shop.catalog.mapper;

import my_shop.catalog.dto.*;
import my_shop.catalog.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", source = "externalId")
    @Mapping(target = "title", source = "name")
    @Mapping(target = "brand", source = "brand.name")
    @Mapping(target = "category", source = "category.name")
    @Mapping(target = "price", expression = "java(extractDefaultPrice(product))")
    @Mapping(target = "productVariantId", expression = "java(extractDefaultVariantId(product))")
    @Mapping(target = "imageUrl", expression = "java(extractPrimaryImage(product))")
    ProductCardResponseDto toProductCardDto(Product product);

    @Mapping(target = "id", source = "externalId")
    ProductDetailResponseDto toProductDetailDto(Product product);

    @Mapping(target = "id", source = "externalId")
    BrandDto toBrandDto(Brand brand);

    @Mapping(target = "id", source = "externalId")
    CategoryDto toCategoryDto(Category category);

    @Mapping(target = "id", source = "externalId")
    VariantDto toVariantDto(ProductVariant variant);

    ImageDto toImagesDto(ProductImage productImage);

    default BigDecimal extractDefaultPrice(Product product) {
        if (product.getVariants() == null || product.getVariants().isEmpty()) return null;
        return product.getVariants().get(0).getPrice();
    }

    default java.util.UUID extractDefaultVariantId(Product product) {
        if (product.getVariants() == null || product.getVariants().isEmpty()) return null;
        return product.getVariants().get(0).getExternalId();
    }

    default String extractPrimaryImage(Product product) {
        if (product.getImages() == null) return null;
        return product.getImages().stream()
                .filter(ProductImage::getPrimary)
                .map(ProductImage::getImageUrl)
                .findFirst()
                .orElse(null);
    }
}
