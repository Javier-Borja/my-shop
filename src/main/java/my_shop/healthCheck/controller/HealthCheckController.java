package my_shop.healthCheck.controller;

import my_shop.healthCheck.dto.HealthCheckResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    private final String appName;

    public HealthCheckController(
            @Value("${spring.application.name}") String appName) {
        this.appName = appName;
    }

    @GetMapping
    public ResponseEntity<HealthCheckResponse> health() {
        HealthCheckResponse response = new HealthCheckResponse(
                "UP",
                appName
        );
        return ResponseEntity.ok(response);
    }
}
