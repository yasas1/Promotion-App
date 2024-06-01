package com.promotionservice.repository;

import com.promotionservice.domain.entity.UserShopSubscription;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserShopSubscriptionRepository extends R2dbcRepository<UserShopSubscription, UserShopSubscription.UserShopSubscriptionPk> {
    Mono<UserShopSubscription> findByUserIdAndShopId(Long userId, Long shopId);
    Flux<UserShopSubscription> findByUserId(Long userId);
    Mono<Void> deleteByUserIdAndShopId(Long userId, Long shopId);
}
