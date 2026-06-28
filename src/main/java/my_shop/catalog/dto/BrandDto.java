package my_shop.catalog.dto;

import java.util.UUID;

public class BrandDto {

    private UUID id;
    private String name;
    private String countryOfOrigin;

    public BrandDto() {
    }

    public BrandDto(UUID id, String name, String countryOfOrigin) {
        this.id = id;
        this.name = name;
        this.countryOfOrigin = countryOfOrigin;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }
}
