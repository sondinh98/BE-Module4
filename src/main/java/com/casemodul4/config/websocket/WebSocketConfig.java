//package com.casemodul4.config.websocket;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.scheduling.TaskScheduler;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
//import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic")
//                .setHeartbeatValue(new long[] {10000, 10000});
//        config.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/websocket")
//                .setAllowedOrigins("*")
//                .withSockJS();
//    }
//
//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//        registration.addDecoratorFactory(webSocketHandler -> new WebSocketHandlerDecorator(webSocketHandler) {
//            @Override
//            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//                super.afterConnectionEstablished(session);
//                session.setTextMessageSizeLimit(64 * 1024);
//                session.setBinaryMessageSizeLimit(64 * 1024);
//            }
//        });
//    }
//
//    @Bean
//    public TaskScheduler heartBeatScheduler() {
//        return new ThreadPoolTaskScheduler();
//    }
//}
