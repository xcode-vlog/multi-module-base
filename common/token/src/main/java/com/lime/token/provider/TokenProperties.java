package com.lime.token.provider;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class TokenProperties {

    public class Experation {
        private Long accessToken;
        private Long refreshToken;

        public Long getAccessToken() {
            return accessToken;
        }
        public void setAccessToken(Long accessToken) {
            this.accessToken = accessToken;
        }
        public Long getRefreshToken() {
            return refreshToken;
        }
        public void setRefreshToken(Long refreshToken) {
            this.refreshToken = refreshToken;
        }
    }

    private String secret;
    private Experation experation = new Experation();

    public TokenProperties() {}

    public TokenProperties(String secret, Long accessToken, Long refreshToken ) {
        this.secret = secret;
        this.experation.setAccessToken(accessToken);
        this.experation.setRefreshToken(refreshToken);
    }

    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    public Long getAccessTokenExpiration() {
        return experation.getAccessToken();
    }
    public void setAccessTokenExpiration(Long accessToken) {
        this.experation.setAccessToken(accessToken);
    }
    public Long getRefreshTokenExpiration() {
        return experation.getRefreshToken();
    }
    public void setRefreshTokenExpiration(Long refreshToken) {
        this.experation.setRefreshToken(refreshToken);
    }

    public Experation getExperation() {
        return experation;
    }
    public void setExperation(Experation experation) {
        this.experation = experation;
    }


}
