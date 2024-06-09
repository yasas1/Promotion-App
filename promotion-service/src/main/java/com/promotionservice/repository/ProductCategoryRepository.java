package com.promotionservice.repository;

import com.promotionservice.domain.entity.ProductCategory;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends R2dbcRepository<ProductCategory, Long> {
    Flux<ProductCategory> findAllByShopId(Long shopId);
    Mono<ProductCategory> findByIdAndShopId(Long id, Long shopId);
    Mono<ProductCategory> findByNameAndShopId(String name, Long shopId);
}
