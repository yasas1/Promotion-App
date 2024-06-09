package com.promotionservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductCategoryDto {
    private Long id;
    private String name;
    private String description;
    private Long shopId;
}
