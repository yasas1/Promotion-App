package com.promotionservice.repository;

import com.promotionservice.domain.entity.UserPreferCategory;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserPreferCategoryRepository extends R2dbcRepository<UserPreferCategory, UserPreferCategory.UserPreferCategoryPk> {
    Mono<UserPreferCategory> findByUserIdAndCategoryId(Long userId, Long categoryId);
    Flux<UserPreferCategory> findByUserId(Long userId);
    Mono<Void> deleteByUserIdAndCategoryId(Long userId, Long categoryId);
}
