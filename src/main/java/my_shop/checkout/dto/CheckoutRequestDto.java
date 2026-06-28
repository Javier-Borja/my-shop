package my_shop.checkout.dto;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class CheckoutRequestDto {

    @NotNull(message = "El ID de la dirección es requerido")
    private UUID addressExternalId;

    public CheckoutRequestDto() {
    }

    public CheckoutRequestDto(UUID addressExternalId) {
        this.addressExternalId = addressExternalId;
    }

    public UUID getAddressExternalId() {
        return addressExternalId;
    }

    public void setAddressExternalId(UUID addressExternalId) {
        this.addressExternalId = addressExternalId;
    }
}
