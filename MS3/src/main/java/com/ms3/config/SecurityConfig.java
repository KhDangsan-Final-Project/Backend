package com.ms3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests((authorizeRequests) -> 
                authorizeRequests
                    .requestMatchers("/ms3/user/insert", "/ms3/user/select").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin().disable();
        return http.build();
    }
}
