package my_shop.cart.controller;

import jakarta.validation.Valid;
import my_shop.cart.dto.CartItemRequestDto;
import my_shop.cart.dto.CartResponseDto;
import my_shop.cart.service.CartService;
import my_shop.users.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponseDto> getMyCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.getCart(user));
    }

    @PatchMapping
    public ResponseEntity<CartResponseDto> updateItem(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CartItemRequestDto cartItemRequestDto) {

        return ResponseEntity.ok(cartService.updateItemQuantity(user, cartItemRequestDto.getVariantId(),
                cartItemRequestDto.getQuantity()));
    }

    @DeleteMapping("/{variantId}")
    public ResponseEntity<CartResponseDto> removeItem(
            @AuthenticationPrincipal User user,
            @PathVariable UUID variantId) {

        CartResponseDto updatedCart = cartService.removeItemCompletely(user, variantId);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<CartResponseDto> clearCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.clearCart(user));
    }
}
