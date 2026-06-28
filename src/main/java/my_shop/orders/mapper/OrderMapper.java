package my_shop.orders.mapper;

import my_shop.orders.dto.OrderItemResponseDto;
import my_shop.orders.dto.OrderResponseDto;
import my_shop.orders.model.Order;
import my_shop.orders.model.OrderItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "shippingAddressSnapshot", target = "shippingAddress")
    OrderResponseDto toOrderResponse(Order order);

    @Mapping(source = "variant.product.name", target = "productName")
    @Mapping(source = "variant.color", target = "color")
    @Mapping(source = "variant.size", target = "size")
    @Mapping(source = "subtotalAtPurchase", target = "subtotal")
    OrderItemResponseDto toOrderItemResponse(OrderItem item);

    @AfterMapping
    default void mapImageUrl(OrderItem item, @MappingTarget OrderItemResponseDto orderItemResponseDto) {
        String color = item.getVariant().getColor();
        var images = item.getVariant().getProduct().getImages();

        if (images == null || images.isEmpty()) {
            orderItemResponseDto.setImageUrl(null);
            return;
        }

        String url = images.stream()
                .filter(img -> img.getColor() != null && img.getColor().equalsIgnoreCase(color))
                .findFirst()
                .map(img -> img.getImageUrl())
                .orElse(images.get(0).getImageUrl());

        orderItemResponseDto.setImageUrl(url);
    }
}
