package com.promotionservice.controller;

import com.promotionservice.domain.dto.ShopDto;
import com.promotionservice.domain.dto.UserDto;
import com.promotionservice.domain.dto.UserPreferCategoryDto;
import com.promotionservice.domain.dto.UserShopSubscriptionDto;
import com.promotionservice.domain.util.ObjectMapper;
import com.promotionservice.domain.util.ResultSetResponse;
import com.promotionservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/promotion-service/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/register",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserDto>> createUser(@Valid @RequestBody UserDto userDto) {
        return this.userService.createUser(userDto)
                .map(shopper -> new ResponseEntity<>(ObjectMapper.userToUserDto(shopper), HttpStatus.CREATED));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SHOP_ADMIN','SHOPPER')")
    @PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserDto>> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        return this.userService.updateUser(userId, userDto)
                .map(shopper -> new ResponseEntity<>(ObjectMapper.userToUserDto(shopper), HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SHOP_ADMIN','SHOPPER')")
    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable Long userId) {
        return this.userService.getUserById(userId)
                .map(shopper -> new ResponseEntity<>(ObjectMapper.userToUserDto(shopper), HttpStatus.OK));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ResultSetResponse<UserDto>>> getAllUsers(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                                           @RequestParam(required = false, defaultValue = "20") int pageSize) {
        return this.userService.getAllUsers(pageNumber, pageSize)
                .map(paged -> new ResponseEntity<>(
                        new ResultSetResponse<>(paged.getNumber(), paged.getSize(), paged.getTotalElements(), paged.getTotalPages(),
                                paged.getContent().stream().map(ObjectMapper::userToUserDto).toList()),
                        HttpStatus.OK));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<HttpStatusCode>> deleteUserById(@PathVariable Long userId) {
        return this.userService.deleteUserById(userId)
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }

    @PreAuthorize("hasAuthority('SHOPPER')")
    @PostMapping(path = "/{userId}/prefer-category", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> createUserPreferCategory(@PathVariable Long userId, @Valid @RequestBody UserPreferCategoryDto userPreferCategoryDto) {
        userPreferCategoryDto.setUserId(userId);
        return this.userService.createUserPreferCategory(userPreferCategoryDto)
                .map(subscription -> new ResponseEntity<>("OK", HttpStatus.CREATED));
    }

    @PreAuthorize("hasAuthority('SHOPPER')")
    @GetMapping(path = "/{userId}/prefer-category", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Long>>> getUserPreferCategoryByUserId(@PathVariable Long userId) {
        return this.userService.getUserPreferCategoriesByUserId(userId)
                .map(categories -> new ResponseEntity<>(categories, HttpStatus.OK));
    }

    @PreAuthorize("hasAuthority('SHOPPER')")
    @DeleteMapping(path = "/{userId}/prefer-category/{categoryId}")
    public Mono<ResponseEntity<String>> deleteSubscribedShopByShopId(@PathVariable Long userId, @PathVariable Long categoryId) {
        return this.userService.deleteUserPreferCategoryByUserIdAndCategoryId(userId, categoryId)
                .then(Mono.fromCallable(() -> new ResponseEntity<>("OK", HttpStatus.OK)));
    }
}
