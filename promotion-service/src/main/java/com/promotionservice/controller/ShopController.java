package com.promotionservice.controller;

import com.promotionservice.domain.dto.ShopBranchDto;
import com.promotionservice.domain.dto.ShopDto;
import com.promotionservice.domain.util.ObjectMapper;
import com.promotionservice.domain.util.ResultSetResponse;
import com.promotionservice.service.ShopService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RequestMapping("/promotion-service/v1/shop")
@RestController
public class ShopController {

    private final ShopService shopService;

    @PreAuthorize("hasAnyAuthority('ADMIN','SHOP_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ShopDto>> createShop(@Valid @RequestBody ShopDto shopDto) {
        return this.shopService.createShop(shopDto)
                .map(shop -> new ResponseEntity<>(ObjectMapper.shopToShopDto(shop), HttpStatus.CREATED));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SHOP_ADMIN')")
    @PutMapping(path = "/{shopId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ShopDto>> updateShop(@PathVariable Long shopId, @Valid @RequestBody ShopDto shopDto) {
        return this.shopService.updateShop(shopId, shopDto)
                .map(shop -> new ResponseEntity<>(ObjectMapper.shopToShopDto(shop), HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SHOP_ADMIN')")
    @GetMapping(path = "/{shopId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ShopDto>> getShopById(@PathVariable Long shopId) {
        return this.shopService.getShopWithBranchesByShopId(shopId)
                .map(shop -> new ResponseEntity<>(ObjectMapper.shopToShopDto(shop), HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ResultSetResponse<ShopDto>>> getAllShops(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                                        @RequestParam(required = false, defaultValue = "20") int pageSize) {
        return this.shopService.getAllShops(pageNumber, pageSize)
                .map(paged -> new ResponseEntity<>(new ResultSetResponse<>(paged.getNumber(), paged.getSize(), paged.getTotalElements(),
                        paged.getTotalPages(), paged.getContent().stream().map(ObjectMapper::shopToShopDto).toList()), HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping(path = "/{shopId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<HttpStatusCode>> deleteShopById(@PathVariable Long shopId) {
        return this.shopService.deleteShopById(shopId)
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SHOP_ADMIN')")
    @PostMapping(path = "/{shopId}/branch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ShopBranchDto>> createShopBranch(@PathVariable Long shopId, @Valid @RequestBody ShopBranchDto shopBranchDto) {
        shopBranchDto.setShopId(shopId);
        return this.shopService.createShopBranch(shopBranchDto)
                .map(shopBranch -> new ResponseEntity<>(ObjectMapper.shopBranchToShopBranchDto(shopBranch), HttpStatus.CREATED));
    }
}
