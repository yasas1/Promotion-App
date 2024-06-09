package com.promotionservice.service.impl;

import com.promotionservice.domain.dto.UserShopSubscriptionDto;
import com.promotionservice.domain.entity.Shop;
import com.promotionservice.domain.entity.UserShopSubscription;
import com.promotionservice.domain.util.ObjectMapper;
import com.promotionservice.repository.UserShopSubscriptionRepository;
import com.promotionservice.service.ShopService;
import com.promotionservice.service.UserService;
import com.promotionservice.service.UserShopSubscriptionService;
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
public class UserShopSubscriptionServiceImpl implements UserShopSubscriptionService {

    private final ShopService shopService;
    private final UserShopSubscriptionRepository userShopSubscriptionRepository;


    @Override
    public Mono<UserShopSubscription> createUserShopSubscription(UserShopSubscriptionDto userShopSubscriptionDto) {

        return this.userShopSubscriptionRepository.findByUserIdAndShopId(userShopSubscriptionDto.getUserId(), userShopSubscriptionDto.getShopId())
                .doOnNext(exists -> log.error("Subscription already exists: {}", exists))
                .flatMap(exists -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Subscription already exists")))
                .switchIfEmpty(Mono.just(userShopSubscriptionDto))
                .flatMap(s -> userShopSubscriptionRepository.save(ObjectMapper.dtoToUserShopSubscription(userShopSubscriptionDto)))
                .doOnNext(shopper -> log.info("User Shop Subscription saved: {}", shopper))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<List<Shop>> getUserShopSubscriptionByUserId(Long userId) {
        return this.userShopSubscriptionRepository.findByUserId(userId)
                .collectList()
                .flatMap(subscriptions -> shopService.getAllShopsByIdIn(subscriptions.stream().map(UserShopSubscription::getShopId).toList())
                                    .collectList());

    }

    @Override
    public Mono<Void> deleteUserShopSubscription(Long userId, Long shopId) {
        return this.userShopSubscriptionRepository.deleteByUserIdAndShopId(userId, shopId);
    }
}
