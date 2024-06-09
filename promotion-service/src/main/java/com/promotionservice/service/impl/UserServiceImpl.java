package com.promotionservice.service.impl;

import com.promotionservice.domain.dto.UserDto;
import com.promotionservice.domain.dto.UserPreferCategoryDto;
import com.promotionservice.domain.entity.User;
import com.promotionservice.domain.entity.UserPreferCategory;
import com.promotionservice.domain.util.ObjectMapper;
import com.promotionservice.repository.UserPreferCategoryRepository;
import com.promotionservice.repository.UserRepository;
import com.promotionservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    public static final String USER_NOT_FOUND_ERROR_MSG = "User not found";
    private final UserRepository userRepository;
    private final UserPreferCategoryRepository userPreferCategoryRepository;
    private final PasswordEncoder passwordEncoder;
    /**
     * @param userDto shopper details
     * @return Shopper
     */
    @Override
    public Mono<User> createUser(UserDto userDto) {

        return Mono.just(ObjectMapper.userDtoToUser(userDto))
                .doOnNext(user -> user.setEmail(userDto.getEmail()))
                .flatMap(user -> this.userRepository.findByEmail(userDto.getEmail())
                        .flatMap(exists -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists")))
                        .switchIfEmpty(Mono.just(user)))
                .flatMap(u -> {
                    User user = (User) u;
                    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    return this.userRepository.save(user);
                })
                .doOnNext(shopper -> log.info("Shopper saved: {}", shopper))
                .doOnError(Throwable::printStackTrace);
    }

    /**
     * @param userDto shopper details
     * @return Shopper
     */
    @Override
    public Mono<User> updateUser(Long id, UserDto userDto) {
        return this.getUserById(id)
                .flatMap(shopper -> this.userRepository.save(ObjectMapper.userDtoToUser(userDto)))
                .doOnError(Throwable::printStackTrace);
    }

    /**
     * @param id shopper id
     */
    @Override
    public Mono<Void> deleteUserById(Long id) {
        return this.getUserById(id)
                .flatMap(this.userRepository::delete)
                .doOnError(Throwable::printStackTrace);
    }

    /**
     * @param id shopper id
     * @return Shopper
     */
    @Override
    public Mono<User> getUserById(Long id) {
        return this.userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_ERROR_MSG)))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<User> findUserByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_ERROR_MSG)))
                .doOnError(Throwable::printStackTrace);
    }

    /**
     * @param pageNumber: page number
     * @param pageSize:   size of the page
     * @return Page<Shopper>
     */
    @Override
    public Mono<Page<User>> getAllUsers(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return this.userRepository.findAllBy(pageRequest)
                .collectList()
                .zipWith(this.userRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), pageRequest, p.getT2()));
    }

    /** User Prefer Category */

    @Override
    public Mono<UserPreferCategory> createUserPreferCategory(UserPreferCategoryDto userPreferCategoryDto) {
        return this.userPreferCategoryRepository.findByUserIdAndCategoryId(userPreferCategoryDto.getUserId(), userPreferCategoryDto.getCategoryId())
                .doOnNext(exists -> log.error("Preference already exists: {}", exists))
                .flatMap(exists -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Preference already exists")))
                .switchIfEmpty(Mono.just(userPreferCategoryDto))
                .flatMap(s -> this.userPreferCategoryRepository.save(UserPreferCategory.builder().userId(userPreferCategoryDto.getUserId())
                        .categoryId(userPreferCategoryDto.getCategoryId()).build()))
                .doOnNext(shopper -> log.info("User Category Preference saved: {}", shopper))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<List<Long>> getUserPreferCategoriesByUserId(Long userId) {
        return this.userPreferCategoryRepository.findByUserId(userId)
                .collectList()
                .map(preference -> preference.stream().map(UserPreferCategory::getCategoryId).toList());
    }

    @Override
    public Mono<Void> deleteUserPreferCategoryByUserIdAndCategoryId(Long userId, Long categoryId) {
        return this.userPreferCategoryRepository.deleteByUserIdAndCategoryId(userId, categoryId);
    }
}
