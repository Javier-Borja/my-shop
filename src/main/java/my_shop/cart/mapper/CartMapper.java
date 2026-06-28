package my_shop.cart.mapper;

import my_shop.cart.dto.CartItemResponseDto;
import my_shop.cart.dto.CartResponseDto;
import my_shop.cart.model.Cart;
import my_shop.cart.model.CartItem;
import my_shop.catalog.model.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "cartId", source = "externalId")
    @Mapping(target = "totalItems", expression = "java(calculateTotalItems(cart))")
    @Mapping(target = "totalAmount", expression = "java(calculateTotalAmount(cart))")
    CartResponseDto toCartResponseDto(Cart cart);

    @Mapping(target = "variantId", source = "variant.externalId")
    @Mapping(target = "productName", source = "variant.product.name")
    @Mapping(target = "sku", source = "variant.sku")
    @Mapping(target = "size", source = "variant.size")
    @Mapping(target = "color", source = "variant.color")
    @Mapping(target = "price", source = "variant.price")
    @Mapping(target = "subtotal", expression = "java(item.getVariant().getPrice().multiply(new BigDecimal(item.getQuantity())))")
    @Mapping(target = "imageUrl", source = "item", qualifiedByName = "resolveVariantImage")
    CartItemResponseDto toCartItemResponseDto(CartItem item);



    @Named("resolveVariantImage")
    default String resolveVariantImage(CartItem item) {
        if (item.getVariant() == null || item.getVariant().getProduct() == null) return null;

        List<ProductImage> images = item.getVariant().getProduct().getImages();
        if (images == null || images.isEmpty()) return null;

        String variantColor = item.getVariant().getColor();

        if (variantColor != null) {
            for (ProductImage img : images) {
                if (variantColor.equalsIgnoreCase(img.getColor())) {
                    return img.getImageUrl();
                }
            }
        }

        return images.stream()
                .filter(img -> Boolean.TRUE.equals(img.getPrimary()))
                .map(ProductImage::getImageUrl)
                .findFirst()
                .orElse(images.get(0).getImageUrl());
    }

    default Integer calculateTotalItems(Cart cart) {
        if (cart.getItems() == null) return 0;
        return cart.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    default BigDecimal calculateTotalAmount(Cart cart) {
        if (cart.getItems() == null) return BigDecimal.ZERO;
        return cart.getItems().stream()
                .map(item -> item.getVariant().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
