package com.promotionservice.config;


import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class UsernamePwdAuthenticationManager implements ReactiveAuthenticationManager {
    private final ReactiveUserDetailsService userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        return this.userService.findByUsername(username)
                .switchIfEmpty(Mono.error(new BadCredentialsException("No user registered with this credentials")))
                .flatMap(userDetails -> {
                    if (this.passwordEncoder.matches(password, userDetails.getPassword())) {
                        return Mono.just(new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities()));
                    } else {
                        return Mono.error(new BadCredentialsException("Invalid credentials"));
                    }
                });
    }
}
