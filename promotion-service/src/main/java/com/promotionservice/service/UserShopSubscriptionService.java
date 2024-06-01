package com.promotionservice.service;

import com.promotionservice.domain.dto.UserShopSubscriptionDto;
import com.promotionservice.domain.entity.Shop;
import com.promotionservice.domain.entity.UserShopSubscription;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserShopSubscriptionService {
    Mono<UserShopSubscription> createUserShopSubscription(UserShopSubscriptionDto userShopSubscriptionDto);
    Mono<List<Shop>> getUserShopSubscriptionByUserId(Long userId);
    Mono<Void> deleteUserShopSubscription(Long userId, Long shopId);
}
