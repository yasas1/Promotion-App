package com.promotionservice.repository;

import com.promotionservice.domain.entity.Shop;
import com.promotionservice.domain.entity.ShopBranch;
import reactor.core.publisher.Mono;

public interface ShopBranchCustomRepository {
    Mono<ShopBranch> saveShopBranch(ShopBranch shopBranch);
    Mono<Shop> findShopWithBranchesByShopId(Long shopId);
}
