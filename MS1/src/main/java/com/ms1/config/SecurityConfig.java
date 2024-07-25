package com.ms1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/actuator/**").authenticated()  // Actuator 엔드포인트 보호
                    .anyRequest().permitAll()  // 다른 모든 요청은 인증 없이 접근 허용
            )
            .httpBasic();  // HTTP Basic 인증 사용
        return http.build();
    }
}
