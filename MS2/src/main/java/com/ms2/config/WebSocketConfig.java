package com.ms2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.ms2.socket.TokenWebSocketHandler;
import com.ms2.socket.UpdateWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  

    @Bean
    public TokenWebSocketHandler tokenWebSocketHandler() {
        return new TokenWebSocketHandler();
    }
    @Bean
    public UpdateWebSocketHandler updateWebSocketHandler() {
        return new UpdateWebSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(tokenWebSocketHandler(), "/ms2/token")
                .setAllowedOrigins("*");
        registry.addHandler(updateWebSocketHandler(), "/ms2/update")
                .setAllowedOrigins("*");
    }
}
