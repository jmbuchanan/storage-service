package com.storage.site.config;

public class JwtResponse {

    private static final long serialVersionUID = -19230810970492L;
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }

}
