package com.promotionservice.service.impl;

import com.promotionservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements ReactiveUserDetailsService {
    private final UserRepository userRepository;
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return this.userRepository.findByEmail(username)
                .map(user -> new org.springframework.security.core.userdetails.User(user.getEmail(),
                        user.getPassword(), List.of(new SimpleGrantedAuthority(user.getUserType().name()))));
    }
}
