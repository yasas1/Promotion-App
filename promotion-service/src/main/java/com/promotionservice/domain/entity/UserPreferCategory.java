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
@Table(name = "public.user_prefer_category")
public class UserPreferCategory {
    @Column(value = "user_id")
    private Long userId;
    @Column(value = "category_id")
    private Long categoryId;
    @Column(value = "created_date_time")
    private Long createdDateTime;

    @Data
    @AllArgsConstructor
    public static class UserPreferCategoryPk implements Serializable {
        private Long userId;
        private Long categoryId;
    }
}
