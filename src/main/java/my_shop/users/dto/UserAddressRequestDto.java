package my_shop.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserAddressRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200)
    private String fullName;

    @NotBlank(message = "El teléfono es obligatorio")
    private String phone;

    @NotBlank(message = "El departamento es obligatorio")
    private String department;

    @NotBlank(message = "El municipio es obligatorio")
    private String municipality;

    @NotBlank(message = "La dirección es obligatoria")
    private String addressLine;

    private Boolean isDefault = false;

    public UserAddressRequestDto() {
    }

    public UserAddressRequestDto(String fullName, String phone, String department,
                                 String municipality, String addressLine, Boolean isDefault) {
        this.fullName = fullName;
        this.phone = phone;
        this.department = department;
        this.municipality = municipality;
        this.addressLine = addressLine;
        this.isDefault = isDefault;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean aDefault) {
        isDefault = aDefault;
    }
}
