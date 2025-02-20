package com.lime.token.provider;

import lombok.Setter;

@Setter
public class TokenProviderConfigurer {

    TokenProperties properties;

    public TokenProviderConfigurer(TokenProperties properties) {
        this.properties = properties;
    }


    public void setSecret(String secret) {
        properties.setSecret(secret);
    }
    public void setAccessTokenExpiration(Long accessTokenExpiration) {
        properties.setAccessTokenExpiration(accessTokenExpiration);
    }
    public void setRefreshTokenExpiration(Long refreshTokenExpiration) {
        properties.setRefreshTokenExpiration(refreshTokenExpiration);
    }

    TokenProperties properties() {
        return properties;
    }

}
