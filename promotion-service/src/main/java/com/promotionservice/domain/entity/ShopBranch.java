package com.promotionservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "public.shop_branch")
public class ShopBranch {
    @Id
    private Long id;
    @Column(value = "name")
    private String name;
    @Column(value = "address")
    private String address;
    @Column(value = "phone1")
    private String phone1;
    @Column(value = "phone2")
    private String phone2;
    @Column(value = "latitude")
    private String latitude;
    @Column(value = "longitude")
    private String longitude;
    @Column(value = "shop_id")
    private Long shopId;

    public static ShopBranch fromRow(Map<String, Object> row) {
        if (row.get("branch_id") != null) {
            return ShopBranch.builder()
                    .id((Long.parseLong(row.get("branch_id").toString())))
                    .name((String) row.get("branch_name"))
                    .address((String) row.get("address"))
                    .phone1((String) row.get("phone1"))
                    .phone2((String) row.get("phone2"))
                    .latitude((String) row.get("latitude"))
                    .longitude((String) row.get("longitude"))
                    .build();
        } else {
            return null;
        }

    }
}
