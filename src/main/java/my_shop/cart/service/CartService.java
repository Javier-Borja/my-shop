package my_shop.cart.service;

import jakarta.transaction.Transactional;
import my_shop.cart.dto.CartResponseDto;
import my_shop.cart.mapper.CartMapper;
import my_shop.cart.model.Cart;
import my_shop.cart.model.CartItem;
import my_shop.cart.repository.CartRepository;
import my_shop.catalog.model.ProductVariant;
import my_shop.catalog.repository.ProductVariantRepository;
import my_shop.common.exceptions.InsufficientStockException;
import my_shop.common.exceptions.OutOfStockException;
import my_shop.common.exceptions.ResourceNotFoundException;
import my_shop.users.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductVariantRepository variantRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository, ProductVariantRepository variantRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.variantRepository = variantRepository;
        this.cartMapper = cartMapper;
    }


    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });
    }

    @Transactional
    public CartResponseDto getCart(User user) {
        return cartMapper.toCartResponseDto(getOrCreateCart(user));
    }

    @Transactional
    public CartResponseDto updateItemQuantity(User user, UUID variantId, Integer change) {
        Cart cart = getOrCreateCart(user);

        ProductVariant variant = variantRepository.findByExternalId(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("Variante de producto no encontrada"));

        int availableStock = variant.getStockQuantity();

        if (availableStock <= 0 && change > 0) {
            throw new OutOfStockException("El producto se ha agotado por completo");
        }

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getVariant().getExternalId().equals(variantId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + change;

            if (newQuantity <= 0) {
                cart.getItems().remove(item);
            } else {
                if (newQuantity > availableStock) {
                    throw new InsufficientStockException("Stock insuficiente");
                }
                item.setQuantity(newQuantity);
            }
        } else {
            if (change > 0) {
                if (change > availableStock) {
                    throw new InsufficientStockException("Stock insuficiente");
                }
                CartItem newItem = new CartItem();
                newItem.setCart(cart);
                newItem.setVariant(variant);
                newItem.setQuantity(change);
                cart.getItems().add(newItem);
            }
        }
        return cartMapper.toCartResponseDto(cart);
    }

    @Transactional
    public CartResponseDto removeItemCompletely(User user, UUID variantId) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));

        boolean removed = cart.getItems().removeIf(item ->
                item.getVariant().getExternalId().equals(variantId));

        if (!removed) {
            throw new ResourceNotFoundException("El producto no se encontró en el carrito");
        }

        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toCartResponseDto(updatedCart);
    }

    @Transactional
    public CartResponseDto clearCart(User user) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un carrito para el usuario actual"));

        if (cart.getItems().isEmpty()) {
            return cartMapper.toCartResponseDto(cart);
        }

        cart.getItems().clear();
        Cart updatedCart = cartRepository.save(cart);

        return cartMapper.toCartResponseDto(updatedCart);
    }
}
