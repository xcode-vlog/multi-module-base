package com.lime.token;

import com.lime.token.provider.JwtTokenProvider;
import com.lime.token.provider.TokenProvider;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TokenProviderTest {

//    @Test
//    @DisplayName("만료일자 없이 토큰 생성")
//    void test1() {
//        TokenProvider tokenProvider = new JwtTokenProvider(
//                "1234".repeat(20),
//                1000*60L,
//                1000*60L
//        );
//        Map<String, Object> claims = new HashMap<>();
////        claims.put("test", "testValue");
//        String notServedExpiredToken = tokenProvider.createToken(claims);
//
//        boolean expiredToken = tokenProvider.isExpiredToken(notServedExpiredToken);
//        Assertions.assertTrue(expiredToken);
//        Claims claims1 = tokenProvider.validateToken(notServedExpiredToken);
//        Assertions.assertNotNull(claims1);
//    }
}
