package com.sas.refactoring;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    // 메시지브로커 설정 - endpoint
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/send");
    }

    @Override
    // 웹소켓 연결 위한 endpoint 설정
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/")
                // .setAllowedOrigins("*")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}