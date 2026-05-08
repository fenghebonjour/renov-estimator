package com.renovSolution.renov.dto;

public class AuthResponse {
    private String token;
    private Long userId;
    private String userType;

    public AuthResponse(String token, Long userId, String userType) {
        this.token = token;
        this.userId = userId;
        this.userType = userType;
    }

    public String getToken() { return token; }
    public Long getUserId() { return userId; }
    public String getUserType() { return userType; }
}
