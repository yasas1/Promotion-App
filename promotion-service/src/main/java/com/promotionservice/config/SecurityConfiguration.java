package com.promotionservice.config;

import com.promotionservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableReactiveMethodSecurity
@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {
    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
        http
                .x509(withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .authorizeExchange(authorize -> authorize
                        .pathMatchers("/promotion-service/v1/users/register/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2Login(withDefaults())
                .httpBasic(withDefaults())
                .formLogin(withDefaults());

        return http.build();

    }

    @Bean
    public ReactiveOAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcReactiveOAuth2UserService delegate = new OidcReactiveOAuth2UserService();
        return userRequest ->
                delegate.loadUser(userRequest)
                        .flatMap(oidcUser -> {
                            String provider = userRequest.getClientRegistration().getRegistrationId();
                            String providerId = (String) oidcUser.getUserInfo().getClaims().get("sub");
                            String email = (String) oidcUser.getUserInfo().getClaims().get("email");
                            return userRepository.findByEmail(email)
                                    .flatMap(user -> {
                                        if (user.getOauth2Provider() == null || !user.getOauth2Provider().equals(provider)) {
                                            user.setOauth2Provider(provider);
                                            user.setOauth2ProviderId(providerId);
                                            return userRepository.save(user);
                                        }
                                        return Mono.just(user);
                                    })
                                    .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException("User not registered"))))
                                    .map(user -> new DefaultOidcUser(Collections.singleton(new SimpleGrantedAuthority(user.getUserType().toString()))
                                            , oidcUser.getIdToken(), oidcUser.getUserInfo()));
                        });
    }
}
