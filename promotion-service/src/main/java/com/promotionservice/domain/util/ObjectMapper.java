package com.promotionservice.domain.util;

import com.promotionservice.domain.dto.*;
import com.promotionservice.domain.entity.*;

import java.util.Objects;

public class ObjectMapper {
    private ObjectMapper() {
    }

    public static User userDtoToUser(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .userType(userDto.getUserType())
                .isVerified(userDto.isVerified())
                .profileImage(userDto.getProfileImage())
                .createdDateTime(System.currentTimeMillis())
                .build();
    }

    public static UserDto userToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userType(user.getUserType())
                .isVerified(false)
                .profileImage(user.getProfileImage())
                .createdDateTime(System.currentTimeMillis())
                .build();
    }

    public static Shop shopDtoToShop(ShopDto shopDto) {
        return Shop.builder()
                .name(shopDto.getName())
                .type(shopDto.getType())
                .isVerified(shopDto.isVerified())
                .coverImage(shopDto.getCoverImage())
                .createdByUserId(shopDto.getCreatedByUserId())
                .createdDateTime(System.currentTimeMillis())
                .build();
    }

    public static ShopDto shopToShopDto(Shop shop) {
        return ShopDto.builder()
                .id(shop.getId())
                .email(shop.getEmail())
                .name(shop.getName())
                .type(shop.getType())
                .isVerified(shop.isVerified())
                .coverImage(shop.getCoverImage())
                .createdByUserId(shop.getCreatedByUserId())
                .createdDateTime(System.currentTimeMillis())
                .branches(shop.getBranches().stream()
                        .map(ObjectMapper::shopBranchToShopBranchDto)
                        .toList())
                .build();
    }

    public static UserShopSubscription dtoToUserShopSubscription(UserShopSubscriptionDto userShopSubscriptionDto) {
        return UserShopSubscription.builder()
                .userId(userShopSubscriptionDto.getUserId())
                .shopId(userShopSubscriptionDto.getShopId())
                .createdDateTime(System.currentTimeMillis())
                .build();
    }

    public static ShopBranch shopBranchDtoToShop(ShopBranchDto shopBranchDto) {
        return ShopBranch.builder()
                .name(shopBranchDto.getName())
                .address(shopBranchDto.getAddress())
                .phone1(shopBranchDto.getPhone1())
                .phone2(shopBranchDto.getPhone2())
                .latitude(shopBranchDto.getLatitude())
                .longitude(shopBranchDto.getLongitude())
                .shopId(shopBranchDto.getShopId())
                .build();
    }

    public static ShopBranchDto shopBranchToShopBranchDto(ShopBranch shopBranch) {
        return ShopBranchDto.builder()
                .id(shopBranch.getId())
                .name(shopBranch.getName())
                .address(shopBranch.getAddress())
                .phone1(shopBranch.getPhone1())
                .phone2(shopBranch.getPhone2())
                .latitude(shopBranch.getLatitude())
                .longitude(shopBranch.getLongitude())
                .shopId(shopBranch.getShopId())
                .build();
    }

    public static ProductCategory productCategoryDtoToProductCategory(ProductCategoryDto productCategoryDto) {
        return ProductCategory.builder()
                .name(productCategoryDto.getName())
                .description(productCategoryDto.getDescription())
                .shopId(productCategoryDto.getShopId())
                .build();
    }

    public static ProductCategoryDto productCategoryToProductCategoryDto(ProductCategory productCategory) {
        return ProductCategoryDto.builder()
                .id(productCategory.getId())
                .name(productCategory.getName())
                .description(productCategory.getDescription())
                .shopId(productCategory.getShopId())
                .build();
    }

    public static Product productDtoToProduct(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .image(productDto.getImage())
                .productCategoryId(productDto.getProductCategoryId())
                .shopId(productDto.getShopId())
                .build();
    }

    public static ProductDto productToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .image(product.getImage())
                .productCategoryId(product.getProductCategoryId())
                .shopId(product.getShopId())
                .build();
    }

}
