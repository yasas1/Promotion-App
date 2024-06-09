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
@Table(name = "public.product")
public class Product {
    @Id
    private Long id;
    @Column(value = "name")
    private String name;
    @Column(value = "description")
    private String description;
    @Column(value = "image")
    private String image;
    @Column(value = "product_category_id")
    private Long productCategoryId;
    @Column(value = "shop_id")
    private Long shopId;
}
