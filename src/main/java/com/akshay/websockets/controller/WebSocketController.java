package com.akshay.websockets.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(@Payload String message) {
    	System.out.println("message- "+message);
        //String username = principal.getName(); // Retrieve the token or user identifier
        return "! Message received: " + message;
    }
}
