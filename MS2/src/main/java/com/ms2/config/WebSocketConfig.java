package com.ms2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.ms2.socket.ChatWebSocketHandler;
import com.ms2.socket.PokemonWebSocketHandler;
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
    @Bean
    public ChatWebSocketHandler chatWebSocketHandler() {
    	return new ChatWebSocketHandler();
    }
    @Bean
    public PokemonWebSocketHandler pokemonWebSocketHandler() {
    	return new PokemonWebSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(tokenWebSocketHandler(), "/ms2/token")
                .setAllowedOrigins("*");
        registry.addHandler(updateWebSocketHandler(), "/ms2/update")
                .setAllowedOrigins("*");
        registry.addHandler(chatWebSocketHandler(), "/ms2/chat")
        .setAllowedOrigins("*");
        registry.addHandler(pokemonWebSocketHandler(), "/ms2/pokemon")
        .setAllowedOrigins("*");
    }
}
