package my_shop.catalog.controller;

import my_shop.catalog.dto.ProductCardResponseDto;
import my_shop.catalog.dto.ProductDetailResponseDto;
import my_shop.catalog.service.ProductService;
import my_shop.common.shared.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductCardResponseDto>> getAllProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, name = "category") String categorySlug,
            @PageableDefault(size = 9, sort = "name") Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts(search, categorySlug, pageable));
    }

    @GetMapping("/{idSlug}")
    public ResponseEntity<ProductDetailResponseDto> getProductDetail(@PathVariable String idSlug) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductDetail(idSlug));
    }
}
