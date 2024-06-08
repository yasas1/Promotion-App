package com.promotionservice.domain.dto;

import com.promotionservice.domain.entity.ShopBranch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShopDto {

    private Long id;
    @NotEmpty
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:\\.[a-zA-Z]{2,})?$",
            message = "Email pattern is not valid")
    private String email;
    @NotEmpty
    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;
    private Long type;
    private boolean isVerified;
    private String coverImage;
    private Long createdByUserId;
    private Long createdDateTime;

    @Builder.Default
    private List<ShopBranchDto> branches = new ArrayList<>();
}
