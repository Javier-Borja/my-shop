package my_shop.cart.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CartResponseDto {
    private UUID cartId;
    private List<CartItemResponseDto> items;
    private Integer totalItems;
    private BigDecimal totalAmount;

    public CartResponseDto() {
    }

    public CartResponseDto(UUID cartId, List<CartItemResponseDto> items,
                           Integer totalItems,
                           BigDecimal totalAmount) {
        this.cartId = cartId;
        this.items = items;
        this.totalItems = totalItems;
        this.totalAmount = totalAmount;
    }

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public List<CartItemResponseDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponseDto> items) {
        this.items = items;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
