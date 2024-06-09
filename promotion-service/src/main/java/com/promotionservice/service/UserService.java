package com.promotionservice.service;

import com.promotionservice.domain.dto.UserDto;
import com.promotionservice.domain.dto.UserPreferCategoryDto;
import com.promotionservice.domain.entity.Shop;
import com.promotionservice.domain.entity.User;
import com.promotionservice.domain.entity.UserPreferCategory;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {

    Mono<User> createUser(UserDto shopperDto);
    Mono<User> updateUser(Long id, UserDto shopperDto);
    Mono<Void> deleteUserById(Long id);
    Mono<User> getUserById(Long id);
    Mono<User> findUserByEmail(String email);
    Mono<Page<User>> getAllUsers(int pageNumber, int pageSize);

    Mono<UserPreferCategory> createUserPreferCategory(UserPreferCategoryDto userPreferCategoryDto);
    Mono<List<Long>> getUserPreferCategoriesByUserId(Long userId);
    Mono<Void> deleteUserPreferCategoryByUserIdAndCategoryId(Long userId, Long categoryId);
}
