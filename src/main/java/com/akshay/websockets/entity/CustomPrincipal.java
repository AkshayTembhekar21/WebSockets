package com.akshay.websockets.entity;

import java.security.Principal;

public class CustomPrincipal implements Principal {
    private String username;

    public CustomPrincipal(String username) {
    	System.out.println("username is - "+ username);
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }
}
