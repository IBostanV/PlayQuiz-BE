package com.ibos.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("#{'${application.security.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;

    private final HandshakeHandler handshakeHandler;

    private final ObjectMapper objectMapper;

    public WebSocketConfig(final HandshakeHandler handshakeHandler,
                           final ObjectMapper objectMapper) {
        this.handshakeHandler = handshakeHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setUserDestinationPrefix("/user");
        config.setApplicationDestinationPrefixes("/antares");
        config.enableSimpleBroker( "/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                .setHandshakeHandler(handshakeHandler)
                .setAllowedOrigins(allowedOrigins.toArray(new String[0]))
                .withSockJS();
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
        return false;
    }
}
