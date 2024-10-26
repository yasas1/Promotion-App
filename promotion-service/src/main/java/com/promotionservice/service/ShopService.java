package com.promotionservice.service;

import com.promotionservice.domain.dto.ShopBranchDto;
import com.promotionservice.domain.dto.ShopDto;
import com.promotionservice.domain.entity.Shop;
import com.promotionservice.domain.entity.ShopBranch;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ShopService {
    Mono<Shop> createShop(ShopDto shopDto);
    Mono<ShopBranch> createShopBranch(ShopBranchDto shopBranchDto);
    Mono<Shop> updateShop(Long id, ShopDto shopDto);
    Mono<Void> deleteShopById(Long id);
    Mono<Shop> getShopById(Long id);
    Mono<Shop> getShopWithBranchesByShopId(Long id);
    Mono<Page<Shop>> getAllShops(int pageNumber, int pageSize);
    Flux<Shop> getAllShopsByIdIn(List<Long> ids);
}
