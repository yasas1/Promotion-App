package com.promotionservice.controller;

import com.promotionservice.domain.dto.UserDto;
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

@AllArgsConstructor
@RequestMapping("/promotion-service/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/register",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserDto>> createShopper(@Valid @RequestBody UserDto userDto) {
        return this.userService.createUser(userDto)
                .map(shopper -> new ResponseEntity<>(ObjectMapper.userToUserDto(shopper), HttpStatus.CREATED));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SHOP_ADMIN','SHOPPER')")
    @PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserDto>> updateShopper(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        return this.userService.updateUser(userId, userDto)
                .map(shopper -> new ResponseEntity<>(ObjectMapper.userToUserDto(shopper), HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SHOP_ADMIN','SHOPPER')")
    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserDto>> getShopperById(@PathVariable Long shopperId) {
        return this.userService.getUserById(shopperId)
                .map(shopper -> new ResponseEntity<>(ObjectMapper.userToUserDto(shopper), HttpStatus.OK));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ResultSetResponse<UserDto>>> getAllShoppers(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                                           @RequestParam(required = false, defaultValue = "20") int pageSize) {
        return this.userService.getAllUsers(pageNumber, pageSize)
                .map(paged -> new ResponseEntity<>(
                        new ResultSetResponse<>(paged.getNumber(), paged.getSize(), paged.getTotalElements(), paged.getTotalPages(),
                                paged.getContent().stream().map(ObjectMapper::userToUserDto).toList()),
                        HttpStatus.OK));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<HttpStatusCode>> deleteShopperById(@PathVariable Long userId) {
        return this.userService.deleteUserById(userId)
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }
}
