package com.lime.token.provider;

import com.lime.token.domain.JwtTokenType;
import com.lime.token.provider.functions.TokenResolver;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.function.Function;

public interface TokenProvider {

    /**
     * 만료 없는 {@code Token} 생성
     * @param claims {@code Token}을 생성하기 위한 {@code payload} 정보
     * @return {@link String} 생성된 {@code token}을 반환한다.
     */
    String createToken(Map<String, Object> claims);

    /**
     * {@code jwt.expiration.access-token}, {@code jwt.expiration.refresh-token} property 참조하여 {@code token} 생성
     * @param claims {@code Token}을 생성하기 위한 {@code payload} 정보
     * @param type {@link JwtTokenType} {@code Token유형}
     * @return
     */
    String createToken(Map<String, Object> claims, JwtTokenType type);

    /**
     * 제공된 만료일자로 {@code Token}을 생성한다.
     * @param claims {@code Token}을 생성하기 위한 {@code payload} 정보
     * @param expiration {@code payload.exp} {@code 만료일자}
     * @return
     */
    String createToken(Map<String, Object> claims, LocalDateTime expiration);

    /**
     * @param resolver {@link Function}을 상속받는 {@link FunctionalInterface} {@code Token}을 받기 위한 {@code Function}
     * @param request {@code Token}정보를 포함하고 있는 {@code Request}객체
     * @return
     */
    String resolvedToken(TokenResolver<HttpServletRequest, String> resolver, ServletRequest request);

    /**
     * {@link Base64.Decoder}를 이용하여 payload를 Map으로 반환한다.
     * @param token {@link #resolvedToken(TokenResolver, ServletRequest)} 반환 객체
     * @return
     */
    Map parsePayload(String token);

    /**
     * {@link #parsePayload}반환 값을 기준으로 {@code "exp"} 값을 {@link LocalDateTime}객체로 변환하여 {@link LocalDateTime#now()}와 비교한다.
     * @param token {@link #resolvedToken(TokenResolver, ServletRequest)} 반환 객체
     * @return
     */
    boolean isExpiredToken(String token);

    /**
     * {@link Jwts#parser()}를 이용하여 Token 정보를 추출 하여 {@code payload}를 {@link Claims} 객체로 반환한다.
     * @param token {@link #resolvedToken(TokenResolver, ServletRequest)} 반환 객체
     * @return
     */
    Claims validateToken(String token);

}
