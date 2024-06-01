package com.promotionservice.domain.entity;

import com.promotionservice.domain.type.UserType;
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
@Table(name = "public.user")
public class User {

    @Id
    private Long id;
    @Column(value = "email")
    private String email;
    @Column(value = "firstname")
    private String firstName;
    @Column(value = "lastname")
    private String lastName;
    @Column(value = "usertype")
    private UserType userType;
    @Column(value = "password")
    private String password;
    @Column(value = "is_verified")
    private boolean isVerified;
    @Column(value = "profile_image")
    private String profileImage;
    @Column(value = "created_date_time")
    private Long createdDateTime;

    public static User fromRow(Map<String, Object> row) {
        if (row.get("id") != null) {
            return User.builder()
                    .id((Long.parseLong(row.get("id").toString())))
                    .firstName((String) row.get("firstname"))
                    .lastName((String) row.get("lastname"))
                    .isVerified((Boolean) row.get("is_verified"))
                    .profileImage((String) row.get("profile_image"))
                    .build();
        } else {
            return null;
        }

    }
}
