package com.promotionservice.repository;

import com.promotionservice.domain.entity.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface ProductRepository extends R2dbcRepository<Product, Long> {
    Flux<Product> findAllByShopIdAndProductCategoryId(Long shopId, Long productCategoryId);
    Mono<Product> findByShopIdAndProductCategoryIdAndName(Long shopId, Long productCategoryId, String name);
    Mono<Product> findByIdAndShopIdAndProductCategoryId(Long id, Long shopId, Long productCategoryId);
}
