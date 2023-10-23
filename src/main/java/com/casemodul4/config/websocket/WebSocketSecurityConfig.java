//package com.casemodul4.config.websocket;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketSecurityConfig implements WebSocketMessageBrokerConfigurer {
//    // ...
//
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        // Cấu hình prefix cho các topic
//        config.enableSimpleBroker("/topic")
//                .setHeartbeatValue(new long[] {10000, 10000}); // Thời gian heartbeat cho server và client
//        config.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        // Đăng ký endpoint cho WebSocket và SockJS
//        registry.addEndpoint("/ws").withSockJS();
//    }
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        // Cấu hình xác thực cho kênh đến của WebSocket
//        registration.interceptors(new WebSocketAuthInterceptor());
//    }
//}
