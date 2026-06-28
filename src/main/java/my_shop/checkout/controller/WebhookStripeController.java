package my_shop.checkout.controller;

import my_shop.checkout.service.StripeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/webhook")
public class WebhookStripeController {

    private final StripeService stripeService;

    public WebhookStripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/stripe")
    public ResponseEntity<Void> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        stripeService.processWebhookEvent(payload, sigHeader);
        return ResponseEntity.ok().build();
    }
}
