package com.ms2.config;

import com.ms2.socket.MyWebSocketHandler;
import com.ms2.socket.TokenWebSocketHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Bean
    public MyWebSocketHandler myWebSocketHandler() {
        return new MyWebSocketHandler();
    }


    @Bean
    public TokenWebSocketHandler tokenWebSocketHandler() {
        return new TokenWebSocketHandler();
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler(), "/ms2/ws")
                .setAllowedOrigins("*");
        
        registry.addHandler(myWebSocketHandler(), "/ms2/token")
                .setAllowedOrigins("*");
    }
}
