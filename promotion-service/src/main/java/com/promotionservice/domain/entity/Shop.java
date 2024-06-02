package com.promotionservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

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
    private Integer type;
    @Column(value = "is_verified")
    private boolean isVerified;
    @Column(value = "cover_image")
    private String coverImage;
    @Column(value = "created_by_user_id")
    private Long createdByUserId;
    @Column(value = "created_date_time")
    private Long createdDateTime;
}
