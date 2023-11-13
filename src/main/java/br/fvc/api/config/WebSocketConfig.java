package br.fvc.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(tutorialHandle(), "/tutorial").setAllowedOrigins("*");
    }

    @Bean
    WebSocketHandler tutorialHandle() {
        return new TutorialHandler();
    }

    // @Override
    // public void configureMessageBroker(MessageBrokerRegistry config) {
    // config.enableSimpleBroker("/topic");
    // config.setApplicationDestinationPrefixes("/app");
    // }

    // @Override
    // public void registerStompEndpoints(StompEndpointRegistry registry) {
    // registry.addEndpoint("/myWebSocket").setAllowedOrigins("*");
    // registry.addEndpoint("/myWebSocket").setAllowedOrigins("*").withSockJS();
    // }

}