package com.lime.token.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.*;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

class TokenUtils {
    private TokenUtils() {}

    static SecretKey getSecretKey(String secretKey) {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKey.getBytes());
        SecretKey encodedKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());

        assert encodedKey != null;

        return encodedKey;
    }

    static Map decodePayload(String token) {
        String encodedPayload = token.split("\\.")[1];
        byte[] decodedPayload = Base64.getDecoder().decode(encodedPayload);
        if(decodedPayload.length == 0){
            return Collections.EMPTY_MAP;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new String(decodedPayload), Map.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
    static Instant toInstant(Object src) {
        return switch (src) {
            case Integer i-> Instant.ofEpochSecond((Long) src);
            case Long l -> Instant.ofEpochMilli((Long) src);
            case Date d -> ((Date) src).toInstant();
            default -> throw new IllegalArgumentException("wrong type need to Integer|Long|Date");
        };
    }
    static LocalDateTime toLocalDateTime(Object src) {
        return switch(src) {
            case LocalDateTime ldt -> ldt;
            default -> LocalDateTime.ofInstant(toInstant(src), ZoneId.systemDefault()) ;
        };
    }

    static boolean isValidClaims(Map<String, Object> claims) {
        if(claims == null){
            throw new IllegalArgumentException("claim is not possible null or empty");
        }
        if(claims.isEmpty()){
            throw new IllegalArgumentException("claim is not possible null or empty");
        }
        return true;
    }


}
