package com.promotionservice.service;

import com.promotionservice.domain.dto.ProductCategoryDto;
import com.promotionservice.domain.dto.ProductDto;
import com.promotionservice.domain.entity.Product;
import com.promotionservice.domain.entity.ProductCategory;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductService {
    Mono<ProductCategory> createProductCategory(ProductCategoryDto productCategoryDto);

    Mono<ProductCategory> updateProductCategory(ProductCategoryDto productCategoryDto);

    Mono<ProductCategory> getProductCategoryById(Long id, Long shopId);

    Mono<List<ProductCategory>> findProductCategoriesByShopId(Long shopId);

    Mono<Void> deleteProductCategoryById(Long id, Long shopId);

    Mono<Product> createProduct(ProductDto productDto);

    Mono<Product> updateProduct(ProductDto productDto);

    Mono<Product> findProductByIdAndCategoryId(Long id, Long shopId, Long productCategoryId);

    Mono<List<Product>> findProductsByShopIdAndCategoryId(Long shopId, Long productCategoryId);

    Mono<Void> deleteProductById(Long id, Long shopId, Long productCategoryId);
}
