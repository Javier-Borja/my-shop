package my_shop.catalog.service;

import jakarta.transaction.Transactional;
import my_shop.catalog.dto.ProductCardResponseDto;
import my_shop.catalog.dto.ProductDetailResponseDto;
import my_shop.catalog.mapper.ProductMapper;
import my_shop.catalog.model.Product;
import my_shop.catalog.repository.ProductRepository;
import my_shop.common.exceptions.ResourceNotFoundException;
import my_shop.common.shared.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public PageResponse<ProductCardResponseDto> getAllProducts(String search, String slug, Pageable pageable) {

        Specification<Product> spec = Specification.allOf(
                ProductRepository.Specs.Search(search),
                ProductRepository.Specs.CategorySlug(slug)
        );

        Page<Product> productPage = productRepository.findAll(spec, pageable);

        return new PageResponse<>(productPage.map(productMapper::toProductCardDto));
    }

    @Transactional
    public ProductDetailResponseDto getProductDetail(String identifier) {
        return productRepository.findBySlugOrExternalId(identifier)
                .map(productMapper::toProductDetailDto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
    }
}
