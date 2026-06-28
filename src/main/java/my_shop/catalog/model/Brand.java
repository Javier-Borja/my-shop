package my_shop.catalog.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", unique = true, nullable = false, updatable = false)
    private UUID externalId;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "country_of_origin", length = 50)
    private String countryOfOrigin;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;

    @PrePersist
    protected void onCreate() { this.externalId = UUID.randomUUID(); }

    public Brand() {}

    public Brand(Long id, UUID externalId, String name, String countryOfOrigin, List<Product> products) {
        this.id = id;
        this.externalId = externalId;
        this.name = name;
        this.countryOfOrigin = countryOfOrigin;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
