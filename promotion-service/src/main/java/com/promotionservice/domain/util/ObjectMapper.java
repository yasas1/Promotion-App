package com.promotionservice.domain.util;

import com.promotionservice.domain.dto.ShopDto;
import com.promotionservice.domain.dto.UserDto;
import com.promotionservice.domain.dto.UserShopSubscriptionDto;
import com.promotionservice.domain.entity.Shop;
import com.promotionservice.domain.entity.User;
import com.promotionservice.domain.entity.UserShopSubscription;

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

    public static UserDto shopperToUserDto(User user) {
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
                .isVerified(false)
                .coverImage(shop.getCoverImage())
                .createdByUserId(shop.getCreatedByUserId())
                .createdDateTime(System.currentTimeMillis())
                .build();
    }

    public static UserShopSubscription dtoToUserShopSubscription(UserShopSubscriptionDto userShopSubscriptionDto) {
        return UserShopSubscription.builder()
                .userId(userShopSubscriptionDto.getUserId())
                .shopId(userShopSubscriptionDto.getShopId())
                .createdDateTime(System.currentTimeMillis())
                .build();
    }

}
