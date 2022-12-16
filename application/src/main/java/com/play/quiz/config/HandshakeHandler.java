package com.play.quiz.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Deprecated
@Component
public class HandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(final ServerHttpRequest request, final WebSocketHandler wsHandler, final Map<String, Object> attributes) {
        Principal principal = request.getPrincipal();

        if (Objects.isNull(principal)) {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ANONYMOUS"));
            principal = new AnonymousAuthenticationToken("WebsocketConfiguration", UUID.randomUUID(), authorities);
        }

        return principal;
    }
}
