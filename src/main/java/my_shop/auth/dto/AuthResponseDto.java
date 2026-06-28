package my_shop.auth.dto;

import java.util.UUID;

public class AuthResponseDto {

    private UUID userId;
    private String email;
    private String firstName;
    private String role;
    private String token;

    public AuthResponseDto() {
    }

    public AuthResponseDto(UUID userId, String email, String firstName, String role, String token) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.role = role;
        this.token = token;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
