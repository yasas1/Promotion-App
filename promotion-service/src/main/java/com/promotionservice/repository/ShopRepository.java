package com.promotionservice.repository;

import com.promotionservice.domain.entity.Shop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Repository
public interface ShopRepository extends R2dbcRepository<Shop, Long> {
    Flux<Shop> findAllBy(Pageable pageable);
    Mono<Shop> findByEmail(String email);
    Flux<Shop> findAllByIdIn(Collection<Long> id);
}
