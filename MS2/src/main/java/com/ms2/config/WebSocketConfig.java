package com.ms2.config;

import com.ms2.socket.BattleWebSocketHandler;
import com.ms2.socket.MyWebSocketHandler;
import com.ms2.socket.RoomWebSocketHandler;
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
    @Bean
    public BattleWebSocketHandler battleWebSocketHandler() {
        return new BattleWebSocketHandler();
    }
    @Bean
    public RoomWebSocketHandler roomWebSocketHandler() {
    	return new RoomWebSocketHandler();
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    	//채팅 서버 
        registry.addHandler(myWebSocketHandler(), "/ms2/ws")
                .setAllowedOrigins("*");
        //로그인 서버
        registry.addHandler(tokenWebSocketHandler(), "/ms2/token")
                .setAllowedOrigins("*");
        //전투 서버
        registry.addHandler(battleWebSocketHandler(), "/ms2/battle")
        .setAllowedOrigins("*");
        //방 id 서버
        registry.addHandler(roomWebSocketHandler(), "/ms2/roomid")
        .setAllowedOrigins("*");
    }
}
