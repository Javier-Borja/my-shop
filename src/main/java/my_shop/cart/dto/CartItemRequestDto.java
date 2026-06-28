package my_shop.cart.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CartItemRequestDto {

    @NotNull(message = "Campo requerido")
    private UUID variantId;

    @NotNull(message = "Campo requerido")
    private Integer quantity;

    public CartItemRequestDto() {
    }

    public CartItemRequestDto(UUID productId, Integer quantity) {
        this.variantId = productId;
        this.quantity = quantity;
    }

    public UUID getVariantId() {
        return variantId;
    }

    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
