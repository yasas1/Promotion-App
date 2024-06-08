package com.promotionservice.controller;

import com.promotionservice.domain.dto.ShopDto;
import com.promotionservice.domain.dto.UserShopSubscriptionDto;
import com.promotionservice.domain.util.ObjectMapper;
import com.promotionservice.service.UserShopSubscriptionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@PreAuthorize("hasAuthority('SHOPPER')")
@AllArgsConstructor
@RequestMapping("/promotion-service/v1/user/{userId}/shop/subscription")
@RestController
public class UserShopSubscriptionController {
    private final UserShopSubscriptionService userShopSubscriptionService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> createUserShopSubscription(@PathVariable Long userId, @Valid @RequestBody UserShopSubscriptionDto userShopSubscriptionDto) {
        userShopSubscriptionDto.setUserId(userId);
        return this.userShopSubscriptionService.createUserShopSubscription(userShopSubscriptionDto)
                .map(subscription -> new ResponseEntity<>("OK", HttpStatus.CREATED));
    }

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<ShopDto>>> getSubscribedShopsByUserId(@PathVariable Long userId) {
        return this.userShopSubscriptionService.getUserShopSubscriptionByUserId(userId)
                .map(shoppers -> new ResponseEntity<>(shoppers.stream()
                        .map(ObjectMapper::shopToShopDto).toList(), HttpStatus.OK));
    }

    @DeleteMapping( path = "/{shopId}")
    public Mono<ResponseEntity<String>> deleteSubscribedShopByShopId(@PathVariable Long userId, @PathVariable Long shopId) {
        return this.userShopSubscriptionService.deleteUserShopSubscription(userId, shopId)
                .then(Mono.fromCallable(() -> new ResponseEntity<>("OK", HttpStatus.OK)));
    }
}
