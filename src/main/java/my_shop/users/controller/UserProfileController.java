package my_shop.users.controller;

import jakarta.validation.Valid;
import my_shop.users.dto.UserProfileResponseDto;
import my_shop.users.dto.UserUpdateRequestDto;
import my_shop.users.model.User;
import my_shop.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserProfileResponseDto> getUserProfile(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUserProfile(user));
    }

    @PatchMapping
    public ResponseEntity<UserProfileResponseDto> updateMyProfile(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        UserProfileResponseDto updated = userService.updateNameUser(user.getId(), userUpdateRequestDto);

        return ResponseEntity.ok(updated);
    }
}
