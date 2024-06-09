package com.promotionservice.controller;

import com.promotionservice.domain.dto.ProductCategoryDto;
import com.promotionservice.domain.dto.ProductDto;
import com.promotionservice.domain.dto.ShopBranchDto;
import com.promotionservice.domain.dto.ShopDto;
import com.promotionservice.domain.util.ObjectMapper;
import com.promotionservice.domain.util.ResultSetResponse;
import com.promotionservice.service.ProductService;
import com.promotionservice.service.ShopService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/promotion-service/v1/shop")
@RestController
public class ShopController {

    private final ShopService shopService;
    private final ProductService productService;

    @PreAuthorize("hasAnyAuthority('ADMIN','SHOP_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ShopDto>> createShop(@Valid @RequestBody ShopDto shopDto) {
        return this.shopService.createShop(shopDto)
                .map(shop -> new ResponseEntity<>(ObjectMapper.shopToShopDto(shop), HttpStatus.CREATED));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SHOP_ADMIN')")
    @PutMapping(path = "/{shopId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ShopDto>> updateShop(@PathVariable Long shopId, @Valid @RequestBody ShopDto shopDto) {
        return this.shopService.updateShop(shopId, shopDto)
                .map(shop -> new ResponseEntity<>(ObjectMapper.shopToShopDto(shop), HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SHOP_ADMIN')")
    @GetMapping(path = "/{shopId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ShopDto>> getShopById(@PathVariable Long shopId) {
        return this.shopService.getShopWithBranchesByShopId(shopId)
                .map(shop -> new ResponseEntity<>(ObjectMapper.shopToShopDto(shop), HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ResultSetResponse<ShopDto>>> getAllShops(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                                        @RequestParam(required = false, defaultValue = "20") int pageSize) {
        return this.shopService.getAllShops(pageNumber, pageSize)
                .map(paged -> new ResponseEntity<>(new ResultSetResponse<>(paged.getNumber(), paged.getSize(), paged.getTotalElements(),
                        paged.getTotalPages(), paged.getContent().stream().map(ObjectMapper::shopToShopDto).toList()), HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping(path = "/{shopId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<HttpStatusCode>> deleteShopById(@PathVariable Long shopId) {
        return this.shopService.deleteShopById(shopId)
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SHOP_ADMIN')")
    @PostMapping(path = "/{shopId}/branch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ShopBranchDto>> createShopBranch(@PathVariable Long shopId, @Valid @RequestBody ShopBranchDto shopBranchDto) {
        shopBranchDto.setShopId(shopId);
        return this.shopService.createShopBranch(shopBranchDto)
                .map(shopBranch -> new ResponseEntity<>(ObjectMapper.shopBranchToShopBranchDto(shopBranch), HttpStatus.CREATED));
    }

    /**
     * product-category
     **/

    @PreAuthorize("hasAnyAuthority('SHOP_ADMIN')")
    @PostMapping(path = "/{shopId}/product-categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductCategoryDto>> createProductCategory(@PathVariable Long shopId,
                                                                          @Valid @RequestBody ProductCategoryDto productCategoryDto) {
        productCategoryDto.setShopId(shopId);
        return this.productService.createProductCategory(productCategoryDto)
                .map(productCategory -> new ResponseEntity<>(ObjectMapper.productCategoryToProductCategoryDto(productCategory), HttpStatus.CREATED));
    }

    @PreAuthorize("hasAnyAuthority('SHOP_ADMIN')")
    @PutMapping(path = "/{shopId}/product-categories/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductCategoryDto>> updateProductCategory(@PathVariable Long shopId,
                                                                          @PathVariable Long id,
                                                                          @Valid @RequestBody ProductCategoryDto productCategoryDto) {
        productCategoryDto.setId(id);
        productCategoryDto.setShopId(shopId);
        return this.productService.updateProductCategory(productCategoryDto)
                .map(productCategory -> new ResponseEntity<>(ObjectMapper.productCategoryToProductCategoryDto(productCategory), HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('SHOP_ADMIN')")
    @GetMapping(path = "/{shopId}/product-categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<ProductCategoryDto>>> getProductCategoriesByShopId(@PathVariable Long shopId) {
        return this.productService.findProductCategoriesByShopId(shopId)
                .map(productCategories -> new ResponseEntity<>(productCategories.stream().map(ObjectMapper::productCategoryToProductCategoryDto).toList(), HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('SHOP_ADMIN')")
    @DeleteMapping(path = "/{shopId}/product-categories/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> deleteProductCategory(@PathVariable Long shopId,
                                                              @PathVariable Long id) {
        return this.productService.deleteProductCategoryById(id, shopId)
                .then(Mono.fromCallable(() -> new ResponseEntity<>("OK", HttpStatus.OK)));
    }

    /**
     * product
     **/

    @PreAuthorize("hasAnyAuthority('SHOP_ADMIN')")
    @PostMapping(path = "/{shopId}/product-categories/{productCategoryId}/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductDto>> createProduct(@PathVariable Long shopId,
                                                          @PathVariable Long productCategoryId,
                                                          @Valid @RequestBody ProductDto productDto) {
        productDto.setShopId(shopId);
        productDto.setProductCategoryId(productCategoryId);
        return this.productService.createProduct(productDto)
                .map(product -> new ResponseEntity<>(ObjectMapper.productToProductDto(product), HttpStatus.CREATED));
    }

    @PreAuthorize("hasAnyAuthority('SHOP_ADMIN')")
    @PutMapping(path = "/{shopId}/product-categories/{productCategoryId}/products/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable Long shopId,
                                                          @PathVariable Long productCategoryId,
                                                          @PathVariable Long id,
                                                          @Valid @RequestBody ProductDto productDto) {
        productDto.setId(id);
        productDto.setShopId(shopId);
        productDto.setProductCategoryId(productCategoryId);
        return this.productService.updateProduct(productDto)
                .map(product -> new ResponseEntity<>(ObjectMapper.productToProductDto(product), HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('SHOP_ADMIN')")
    @GetMapping(path = "/{shopId}/product-categories/{productCategoryId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<ProductDto>>> getProductsByShopIdAndCategoryId(@PathVariable Long shopId,
                                                                                   @PathVariable Long productCategoryId) {
        return this.productService.findProductsByShopIdAndCategoryId(shopId, productCategoryId)
                .map(productCategories -> new ResponseEntity<>(productCategories.stream().map(ObjectMapper::productToProductDto).toList(), HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('SHOP_ADMIN')")
    @DeleteMapping(path = "/{shopId}/product-categories/{productCategoryId}/products/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> deleteProduct(@PathVariable Long shopId,
                                                      @PathVariable Long productCategoryId,
                                                      @PathVariable Long id) {
        return this.productService.deleteProductById(id, shopId, productCategoryId)
                .then(Mono.fromCallable(() -> new ResponseEntity<>("OK", HttpStatus.OK)));
    }
}
