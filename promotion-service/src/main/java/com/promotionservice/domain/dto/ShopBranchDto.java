package com.promotionservice.domain.dto;

import com.promotionservice.domain.validator.LatitudeConstraint;
import com.promotionservice.domain.validator.LongitudeConstraint;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String address;
    private String phone1;
    private String phone2;
    @NotBlank(message = "Latitude is mandatory")
    @LatitudeConstraint
    private String latitude;
    @NotBlank(message = "Longitude is mandatory")
    @LongitudeConstraint
    private String longitude;
    private Long shopId;
}
