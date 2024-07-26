package com.ms2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.ms2.socket.MyWebSocketHandler;
import com.ms2.socket.RoomWebSocketHandler;
import com.ms2.socket.TokenWebSocketHandler;
import com.ms2.socket.UpdateWebSocketHandler;

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
    
    @Bean
    public RoomWebSocketHandler roomWebSocketHandler() {
        return new RoomWebSocketHandler();
    }
    
    @Bean
    public UpdateWebSocketHandler updateWebSocketHandler() {
        return new UpdateWebSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler(), "/ms2/ws")
                .setAllowedOrigins("*");
        registry.addHandler(tokenWebSocketHandler(), "/ms2/token")
                .setAllowedOrigins("*");
        registry.addHandler(roomWebSocketHandler(), "/ms2/roomid")
                .setAllowedOrigins("*");
        registry.addHandler(updateWebSocketHandler(), "/ms2/update")
                .setAllowedOrigins("*");
    }
}
