package com.promotionservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.promotionservice.domain.type.UserType;
import com.promotionservice.domain.validator.UserTypeSubset;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {

    private Long id;
    @NotEmpty
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:\\.[a-zA-Z]{2,})?$",
            message = "Email pattern is not valid")
    private String email;
    @NotEmpty
    @Size(min = 2, message = "First name should have at least 2 characters")
    private String firstName;
    @NotEmpty
    @Size(min = 2, message = "Last name should have at least 2 characters")
    private String lastName;
    @NotNull
    @UserTypeSubset(anyOf = {UserType.SHOPPER, UserType.SHOP_ADMIN})
    private UserType userType;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String oauth2Provider;
    private String oauth2ProviderId;
    private boolean isVerified;
    private String profileImage;
    private Long createdDateTime;
}
