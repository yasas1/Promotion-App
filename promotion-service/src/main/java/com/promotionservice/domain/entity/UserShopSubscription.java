package com.promotionservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "public.user_shop_subscription")
public class UserShopSubscription {

    @Column(value = "user_id")
    private Long userId;
    @Column(value = "shop_id")
    private Long shopId;
    @Column(value = "created_date_time")
    private Long createdDateTime;

    @Data
    @AllArgsConstructor
    public static class UserShopSubscriptionPk implements Serializable {
        private Long userId;
        private Long shopId;
    }
}
