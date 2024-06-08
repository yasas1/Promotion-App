package com.promotionservice.repository.impl;

import com.promotionservice.domain.entity.Shop;
import com.promotionservice.domain.entity.ShopBranch;
import com.promotionservice.repository.ShopBranchCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class ShopBranchCustomRepositoryImpl implements ShopBranchCustomRepository {
    private final DatabaseClient databaseClient;

    @Transactional
    @Override
    public Mono<ShopBranch> saveShopBranch(ShopBranch shopBranch) {
        String insertQuery = "INSERT INTO public.shop_branch(name, address, phone1, phone2, latitude, longitude, location_geom, shop_id) " +
                "VALUES (:name, :address, :phone1, :phone2, :latitude, :longitude, ST_GeomFromText(:point, 4326), :shopId);";
        String point = "point (" + shopBranch.getLatitude() + " " + shopBranch.getLongitude() + ")";
        return this.databaseClient.sql(insertQuery)
                .bind("name", shopBranch.getName())
                .bind("address", shopBranch.getAddress())
                .bind("phone1", shopBranch.getPhone1())
                .bind("phone2", shopBranch.getPhone2())
                .bind("latitude", shopBranch.getLatitude())
                .bind("longitude", shopBranch.getLongitude())
                .bind("point", point)
                .bind("shopId", shopBranch.getShopId())
                .fetch().first()
                .doOnNext(result -> shopBranch.setId(Long.parseLong(result.get("id").toString())))
                .thenReturn(shopBranch);
    }

    @Override
    public Mono<Shop> findShopWithBranchesByShopId(Long shopId) {
        String selectQuery = "SELECT shop.id as shop_id, shop.email, shop.name as shop_name, shop.type, shop.is_verified, " +
                "shop.is_verified, shop.cover_image, shop.created_by_user_id, shop.created_date_time, " +
                "branch.id as branch_id, branch.name as branch_name, branch.address, branch.phone1, branch.phone2, branch.latitude, branch.longitude " +
                "FROM public.shop as shop " +
                "inner join public.shop_branch as branch " +
                "on shop.id = branch.shop_id " +
                "where shop.id = :shopId;";
        return this.databaseClient.sql(selectQuery)
                .bind("shopId", shopId)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("shop_id"))
                .flatMap(Shop::fromRows)
                .singleOrEmpty();
    }

}
