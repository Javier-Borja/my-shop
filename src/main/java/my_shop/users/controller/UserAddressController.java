package my_shop.users.controller;

import jakarta.validation.Valid;
import my_shop.users.dto.UserAddressRequestDto;
import my_shop.users.dto.UserAddressResponseDto;
import my_shop.users.model.User;
import my_shop.users.service.UserAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user-address")
public class UserAddressController {

    private final UserAddressService userAddressService;

    public UserAddressController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    @GetMapping
    public ResponseEntity<List<UserAddressResponseDto>> getAllAddress(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userAddressService.getAllAddress(user));
    }

    @PostMapping
    public ResponseEntity<List<UserAddressResponseDto>> createAddress(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UserAddressRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userAddressService.createAddressUser(user, request));
    }

    @PatchMapping("/{externalId}/set-default")
    public ResponseEntity<List<UserAddressResponseDto>> setDefaultAddress(
            @AuthenticationPrincipal User user,
            @PathVariable UUID externalId) {

        return ResponseEntity.ok(userAddressService.setDefaultAddress(user, externalId));
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<List<UserAddressResponseDto>> deleteAddress(
            @AuthenticationPrincipal User user,
            @PathVariable UUID externalId) {
        return ResponseEntity.ok(userAddressService.deleteAddress(user, externalId));
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<List<UserAddressResponseDto>> updateAddress(
            @AuthenticationPrincipal User user,
            @PathVariable UUID externalId,
            @Valid @RequestBody UserAddressRequestDto request) {
        return ResponseEntity.ok(userAddressService.updateAddress(user, externalId, request));
    }
}
