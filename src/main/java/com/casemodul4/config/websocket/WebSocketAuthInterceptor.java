//package com.casemodul4.config.websocket;
//
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.security.access.AccessDeniedException;
//
//import java.security.Principal;
//
//public class WebSocketAuthInterceptor implements ChannelInterceptor {
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        Principal userPrincipal = accessor.getUser();
//        if (userPrincipal == null) {
//            throw new AccessDeniedException("Not authenticated!");
//        }
//        return message;
//    }
//}
