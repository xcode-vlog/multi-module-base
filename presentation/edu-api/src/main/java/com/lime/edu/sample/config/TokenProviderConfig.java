package com.lime.edu.sample.config;

import com.lime.token.provider.TokenProperties;
import com.lime.token.provider.JwtTokenProvider;
import com.lime.token.provider.TokenProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenProviderConfig {

    @Bean
    @ConfigurationProperties(prefix = "jwt")
    public TokenProperties tokenProperties() {
        return new TokenProperties();
    }
    @Bean
    public TokenProvider jwtTokenProvider(TokenProperties tokenProperties) {
        return new JwtTokenProvider(tokenProperties);
    }
}
