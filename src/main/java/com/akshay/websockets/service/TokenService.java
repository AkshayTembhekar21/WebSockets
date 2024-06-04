package com.akshay.websockets.service;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

    public boolean validateToken(String token) {
    	System.out.println("token - "+ token);
    	return true;
        //return "valid-token".equals(token);
    }
    
    public String getUsernameFromToken(String token) {
        // Add your logic to retrieve the username from the token
        return "user"; // Change this to actual logic to extract username
    }
}
