package com.promotionservice.service.impl;

import com.promotionservice.domain.dto.ProductCategoryDto;
import com.promotionservice.domain.dto.ProductDto;
import com.promotionservice.domain.entity.Product;
import com.promotionservice.domain.entity.ProductCategory;
import com.promotionservice.domain.util.ObjectMapper;
import com.promotionservice.repository.ProductCategoryRepository;
import com.promotionservice.repository.ProductRepository;
import com.promotionservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    protected final ProductCategoryRepository productCategoryRepository;
    protected final ProductRepository productRepository;

    @Override
    public Mono<ProductCategory> createProductCategory(ProductCategoryDto productCategoryDto) {
        return this.productCategoryRepository.findByNameAndShopId(productCategoryDto.getName(), productCategoryDto.getShopId())
                .doOnNext(exists -> log.error("Category name  already exists: {}", exists))
                .flatMap(exists -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists")))
                .switchIfEmpty(Mono.just(ObjectMapper.productCategoryDtoToProductCategory(productCategoryDto)))
                .flatMap(p -> this.productCategoryRepository.save((ProductCategory) p))
                .doOnNext(category -> log.info("Product category saved: {}", category))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<ProductCategory> updateProductCategory(ProductCategoryDto productCategoryDto) {
        return this.getProductCategoryById(productCategoryDto.getId(), productCategoryDto.getShopId())
                .flatMap(productCategory -> this.productCategoryRepository.save(ObjectMapper.productCategoryDtoToProductCategory(productCategoryDto)))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<ProductCategory> getProductCategoryById(Long id, Long shopId) {
        return this.productCategoryRepository.findByIdAndShopId(id, shopId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product category not found")))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<List<ProductCategory>> findProductCategoriesByShopId(Long shopId) {
        return this.productCategoryRepository.findAllByShopId(shopId)
                .collectList()
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<Void> deleteProductCategoryById(Long id, Long shopId) {
        return this.getProductCategoryById(id, shopId)
                .flatMap(productCategory -> this.productRepository.findAllByShopIdAndProductCategoryId(shopId, id)
                        .flatMap(exists -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "This category has attached to product")))
                        .switchIfEmpty(this.productCategoryRepository.delete(productCategory))
                        .then()
                )
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<Product> createProduct(ProductDto productDto) {
        return this.productRepository.findByShopIdAndProductCategoryIdAndName(productDto.getShopId(), productDto.getProductCategoryId(), productDto.getName())
                .doOnNext(exists -> log.error("Product name  already exists in this category: {}", exists))
                .flatMap(exists -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Product name  already exists in this category")))
                .switchIfEmpty(Mono.just(ObjectMapper.productDtoToProduct(productDto)))
                .flatMap(p -> this.productRepository.save((Product) p))
                .doOnNext(category -> log.info("Product saved: {}", category))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<Product> updateProduct(ProductDto productDto) {
        return this.findProductByIdAndCategoryId(productDto.getId(), productDto.getShopId(), productDto.getProductCategoryId())
                .flatMap(product -> this.productRepository.save(ObjectMapper.productDtoToProduct(productDto)))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<Product> findProductByIdAndCategoryId(Long id, Long shopId, Long productCategoryId) {
        return this.productRepository.findByIdAndShopIdAndProductCategoryId(id, shopId, productCategoryId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<List<Product>> findProductsByShopIdAndCategoryId(Long shopId, Long productCategoryId) {
        return this.productRepository.findAllByShopIdAndProductCategoryId(shopId, productCategoryId)
                .collectList();
    }

    @Override
    public Mono<Void> deleteProductById(Long id, Long shopId, Long productCategoryId) {
        return this.findProductByIdAndCategoryId(id, shopId, productCategoryId)
                .flatMap(this.productRepository::delete)
                .then();
    }
}
