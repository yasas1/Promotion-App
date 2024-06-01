package com.promotionservice.service;

import com.promotionservice.domain.dto.UserDto;
import com.promotionservice.domain.entity.User;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> createUser(UserDto shopperDto);
    Mono<User> updateUser(Long id, UserDto shopperDto);
    Mono<Void> deleteUserById(Long id);
    Mono<User> getUserById(Long id);
    Mono<User> findUserByEmail(String email);
    Mono<Page<User>> getAllUsers(int pageNumber, int pageSize);
}
