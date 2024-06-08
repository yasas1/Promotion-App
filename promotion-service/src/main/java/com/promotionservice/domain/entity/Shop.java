package com.promotionservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "public.shop")
public class Shop {

    @Id
    private Long id;
    @Column(value = "email")
    private String email;
    @Column(value = "name")
    private String name;
    @Column(value = "type")
    private Long type;
    @Column(value = "is_verified")
    private boolean isVerified;
    @Column(value = "cover_image")
    private String coverImage;
    @Column(value = "created_by_user_id")
    private Long createdByUserId;
    @Column(value = "c")
    private Long createdDateTime;

    @Builder.Default
    private List<ShopBranch> branches = new ArrayList<>();

    public static Mono<Shop> fromRows(List<Map<String, Object>> rows) {
        return Mono.just(Shop.builder()
                .id((Long.parseLong(rows.get(0).get("shop_id").toString())))
                .email((String) rows.get(0).get("email"))
                .name((String) rows.get(0).get("shop_name"))
                .type((Long) rows.get(0).get("type"))
                .isVerified((boolean) rows.get(0).get("is_verified"))
                .coverImage((String) rows.get(0).get("coverImage"))
                .createdByUserId((Long) rows.get(0).get("created_by_user_id"))
                .createdDateTime((Long) rows.get(0).get("created_date_time"))
                .branches(rows.stream()
                        .map(ShopBranch::fromRow)
                        .filter(Objects::nonNull)
                        .toList())
                .build());
    }
}
