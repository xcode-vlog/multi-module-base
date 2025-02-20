package com.lime.token.config;

import com.lime.token.provider.JwtTokenProvider;
import com.lime.token.provider.TokenProperties;
import com.lime.token.provider.TokenProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TokenProperties.class)
public class TokenConfig {

    @Bean
    @ConditionalOnMissingBean(TokenProvider.class)
    public TokenProvider tokenProvider(TokenProperties properties) {
        TokenProvider tokenProvider = new JwtTokenProvider(properties);

        return tokenProvider;
    }
}
