package br.fvc.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
// @EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketConfigurer {

    // WebSocketMessageBrokerConfigurer,
    // @Override
    // public void configureMessageBroker(MessageBrokerRegistry config) {
    // config.enableSimpleBroker("/topic");
    // config.setApplicationDestinationPrefixes("/app");
    // }

    // @Override
    // public void registerStompEndpoints(StompEndpointRegistry registry) {
    // registry.addEndpoint("/myHandler").setAllowedOrigins("*");
    // registry.addEndpoint("/myHandler").setAllowedOrigins("*").withSockJS();
    // }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/myHandler")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }

    @Bean
    WebSocketHandler myHandler() {
        return new TutorialHandler();
    }
}

// @Configuration
// @EnableWebSocket
// public class WebSocketConfig implements WebSocketConfigurer {

// @Override
// public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
// registry.addHandler(myHandler(), "/myHandler")
// .addInterceptors(new HttpSessionHandshakeInterceptor());
// }

// @Bean
// public WebSocketHandler myHandler() {
// return new TutorialHandler();
// }

// }