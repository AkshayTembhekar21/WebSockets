package com.akshay.websockets.config;

import java.security.Principal;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.akshay.websockets.entity.CustomPrincipal;
import com.akshay.websockets.service.TokenService;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final TokenService tokenService;

	public WebSocketConfig(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		System.out.println("Inside registerStompEndpoints()");
		registry.addEndpoint("/ws").setHandshakeHandler(new CustomHandshakeHandler(tokenService))
				.setAllowedOrigins("http://localhost:3000") // Allow requests from your frontend
				.withSockJS();
	}

	private static class CustomHandshakeHandler extends DefaultHandshakeHandler {
		private final TokenService tokenService;

		public CustomHandshakeHandler(TokenService tokenService) {
			this.tokenService = tokenService;
		}

		@Override
		protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
		        Map<String, Object> attributes) {
			System.out.println("Inside determineUser()");
			System.out.println("request - "+ request);
			System.out.println("wsHandler - "+ wsHandler);
			System.out.println("attributes - "+ attributes);
		    HttpHeaders headers = request.getHeaders();
		    System.out.println("heasers - "+ headers);
		    String authHeader = headers.getFirst("Authorization");
		    System.out.println("authHeader - "+ authHeader);
		    if (authHeader != null && authHeader.startsWith("Bearer ")) {
		        String token = authHeader.substring(7);
		        if (tokenService.validateToken(token)) {
		        	System.out.println("token is valid");
		            String username = tokenService.getUsernameFromToken(token);
		            return new CustomPrincipal(username);
		        }
		    }
		    return null;
		}
	}
}