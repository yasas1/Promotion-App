package com.promotionservice.service.impl;

import com.promotionservice.domain.dto.ShopBranchDto;
import com.promotionservice.domain.dto.ShopDto;
import com.promotionservice.domain.entity.Shop;
import com.promotionservice.domain.entity.ShopBranch;
import com.promotionservice.domain.util.ObjectMapper;
import com.promotionservice.repository.ShopBranchCustomRepository;
import com.promotionservice.repository.ShopRepository;
import com.promotionservice.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final ShopBranchCustomRepository shopBranchRepository;

    @Override
    public Mono<Shop> createShop(ShopDto shopDto) {
        return Mono.just(ObjectMapper.shopDtoToShop(shopDto))
                .doOnNext(shop -> shop.setEmail(shopDto.getEmail()))
                .flatMap(shop -> this.shopRepository.findByEmail(shopDto.getEmail())
                        .doOnNext(exists -> log.error("Email already exists: {}", exists))
                        .flatMap(exists -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists")))
                        .switchIfEmpty(Mono.just(shop)))
                .flatMap(s -> this.shopRepository.save((Shop) s))
                .doOnNext(shopper -> log.info("Shop saved: {}", shopper))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<Shop> updateShop(Long id, ShopDto shopDto) {
        return this.getShopById(id)
                .flatMap(shopper -> this.shopRepository.save(ObjectMapper.shopDtoToShop(shopDto)))
                .doOnError(Throwable::printStackTrace);
    }


    @Override
    public Mono<Void> deleteShopById(Long id) {
        return this.getShopById(id)
                .flatMap(this.shopRepository::delete)
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<Shop> getShopById(Long id) {
        return this.shopRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found")))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<Shop> getShopWithBranchesByShopId(Long id) {
        return this.shopBranchRepository.findShopWithBranchesByShopId(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found")))
                .doOnError(Throwable::printStackTrace);
    }


    @Override
    public Mono<Page<Shop>> getAllShops(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return this.shopRepository.findAllBy(pageRequest)
                .collectList()
                .zipWith(this.shopRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), pageRequest, p.getT2()));
    }

    @Override
    public Flux<Shop> getAllShopsByIdIn(List<Long> ids) {
        return this.shopRepository.findAllByIdIn(ids);
    }


    @Override
    public Mono<ShopBranch> createShopBranch(ShopBranchDto shopBranchDto) {
        return this.shopBranchRepository.saveShopBranch(ObjectMapper.shopBranchDtoToShop(shopBranchDto));
    }
}
