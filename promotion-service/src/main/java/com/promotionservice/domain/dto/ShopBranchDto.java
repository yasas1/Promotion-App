package com.promotionservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ShopBranchDto {
    private Long id;
    private String name;
    private String address;
    private String phone1;
    private String phone2;
    private String latitude;
    private String longitude;
    private Long shopId;
}
