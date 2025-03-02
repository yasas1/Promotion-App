package com.promotionservice.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostgresConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionConfiguration(
                PostgresqlConnectionFactory.builder()
                        .host("localhost")
                        .port(5432)
                        .database("yourdatabase")
                        .username("yourusername")
                        .password("yourpassword")
                        .codecRegistrar(PostgresqlConnectionFactoryProvider.builder().codecRegistrar())
                        .build()
        );
    }
}
