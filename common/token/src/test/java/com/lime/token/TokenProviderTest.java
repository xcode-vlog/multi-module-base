package com.lime.token;

import com.lime.token.provider.JwtTokenProvider;
import com.lime.token.provider.TokenProperties;
import com.lime.token.provider.TokenProvider;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class TokenProviderTest {

//    String randomkey = Runtime.getRuntime().exec("openssl");
    String randomkey =
        new String(
            Runtime.getRuntime()
                    .exec(new String[]{"openssl", "rand", "-hex", "64"})
                    .getInputStream()
                    .readAllBytes()
        ).replaceAll(System.lineSeparator(), "");
    TokenProvider tokenProvider;

    public TokenProviderTest() throws IOException {
    }

    @BeforeEach
    void setUp() throws IOException {

        TokenProperties tokenProperties = new TokenProperties();
        tokenProperties.setAccessTokenExpiration(3000L);
        tokenProperties.setRefreshTokenExpiration(3000L);
        tokenProperties.setSecret(randomkey);
        this.tokenProvider = new JwtTokenProvider(tokenProperties);
    }

    @DisplayName("만료일자 없이 토큰 생성")
    @ParameterizedTest
    @ValueSource(strings = {"randomString", "testStr", "test", "1234"})
    void test1(String input) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", input);
        String notServedExpiredToken = tokenProvider.createToken(claims);

        boolean expiredToken = tokenProvider.isExpiredToken(notServedExpiredToken);
        Assertions.assertTrue(expiredToken);
        Claims claims1 = tokenProvider.validateToken(notServedExpiredToken);
        Assertions.assertNotNull(claims1);
        Assertions.assertEquals(input, claims1.getSubject());
    }
}
