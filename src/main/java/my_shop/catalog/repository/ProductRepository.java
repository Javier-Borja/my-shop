package my_shop.catalog.repository;

import jakarta.persistence.criteria.Predicate;
import my_shop.catalog.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT p FROM Product p WHERE p.slug = :identifier OR CAST(p.externalId AS string) = :identifier")
    @EntityGraph(attributePaths = {"brand", "category"})
    Optional<Product> findBySlugOrExternalId(@Param("identifier") String identifier);

    @Override
    @EntityGraph(attributePaths = {"brand", "category"})
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    interface Specs {
        static Specification<Product> Search(String search) {
            return (root, query, cb) -> {
                if (search == null || search.isBlank()) {
                    return null;
                }
                String pattern = "%" + search.toLowerCase() + "%";
                Predicate namePredicate = cb.like(cb.lower(root.get("name")), pattern);

                return cb.or(namePredicate);
            };
        }

        static Specification<Product> CategorySlug(String slug) {
            return (root, query, cb) -> (slug == null || slug.isBlank())
                    ? null : cb.equal(root.get("category").get("slug"), slug);
        }
    }

}
