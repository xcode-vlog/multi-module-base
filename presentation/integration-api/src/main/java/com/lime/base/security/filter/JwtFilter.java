package com.lime.base.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lime.base.security.config.PermitRequestRegistry;
import com.lime.token.provider.TokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final PermitRequestRegistry permitRequestRegistry;
    private final AntPathMatcher pathMatcher;
    
    

    public JwtFilter(TokenProvider tokenProvider, PermitRequestRegistry permitRequestRegistry, AntPathMatcher pathMatcher) {
        this.tokenProvider = tokenProvider;
        this.permitRequestRegistry = permitRequestRegistry;
		this.pathMatcher = pathMatcher;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
    	String requestURI = request.getRequestURI();
        logger.info("requestURI :::: "+requestURI);
        
        try {
	    	//token resolving
	        String token = tokenProvider.resolvedToken( (httpRequest)-> {
	        	// resolveToken 인터페이스 함수 람다식 표현
	        	
	        	String bearerToken = httpRequest.getHeader("Authorization"); // 헤더에서 꺼내오기 
	        	
	        	logger.info("bearerToken 확인 :" + bearerToken);
	        	
	            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
	            	// bearerToken 이 null 이 아니면서, Bearer 로 시작한다면
	            	
	            	logger.info("정상적인 토큰 확인 완료");
	                return bearerToken.substring(7); // "Bearer " 제거 후 토큰 반환
	                
	            }else {
	            	
	            	logger.info("header에서 꺼내올수 없는 토큰 확인");
	            	
	            	return  Optional.ofNullable(bearerToken)
	            			.orElse("INVALID");
	            	// wrapper class Optional로 null이라면 ~
	            	
	            } // end of else
	        	
	        },request);
	        
	        
	        // header 에서 꺼내올수 없는 비정상 토큰일때 throw
	        if ("INVALID".equals(token) || !request.getHeader("Authorization").startsWith("Bearer ")) 
	        	throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
	                      
	        	
    		boolean expiredChk = tokenProvider.isExpiredToken(token);
    		// token 이 만료된 토큰인지 검사하기
    		if (expiredChk) 
    			throw new ExpiredJwtException(null, null, "유효기간 만료 토큰 확인 완료");
    		
    		
			// 만료 토큰이 아니라면 아래 진행
    		Claims claims = tokenProvider.validateToken(token);
        	// resolve된 token 으로 Claim 반환
    		
        	Collection<? extends GrantedAuthority> authorities = 
        			Arrays.stream(claims.get("sub").toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList()); 

            User principal = new User(claims.getSubject(), "", authorities);
        	
            // authenticator
            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, token, authorities);
            
            // contextholder
            SecurityContextHolder.getContext().setAuthentication(authentication);
    		
			
        } catch (SecurityException | MalformedJwtException e) {
        	
        	setResponse(requestURI, response, HttpStatus.UNAUTHORIZED, e, "잘못된 JWT 서명입니다.");
        	
        } catch (ExpiredJwtException e) {
            
        	setResponse(requestURI, response, HttpStatus.UNAUTHORIZED, e, "만료된 JWT 토큰입니다.");
        	
        } catch (UnsupportedJwtException e) {
            
        	setResponse(requestURI, response, HttpStatus.UNAUTHORIZED, e, "지원되지 않는 JWT 토큰입니다.");
        	
        } catch (IllegalArgumentException e) {
        	
            setResponse(requestURI, response, HttpStatus.UNAUTHORIZED, e, "유효하지 않은 JWT 토큰입니다.");
            
        } catch (Exception e) {
        	
            setResponse(requestURI, response, HttpStatus.INTERNAL_SERVER_ERROR, e, "서버 내부 오류 발생");
        }
        	
        filterChain.doFilter(request, response);
        
    } // ==== end of doFilterInternal =====
    
    
    private void setResponse(String requestURI, HttpServletResponse response, HttpStatus status, Throwable e, String message) throws RuntimeException, IOException {
        
    	// 참조 사이트
    	// https://yooniversal.github.io/project/post256/ , https://jjhwang.tistory.com/14
    	
    	ObjectMapper objectMapper = new ObjectMapper();
        
        Map<String, Object> errorResponse = new HashMap<>();
        
        errorResponse.put("timestamp", LocalDateTime.now().toString()); // 발생시간
        errorResponse.put("path", requestURI); // 발생한 URL
        errorResponse.put("status", status.value() ); // HTTP 에러코드
        errorResponse.put("error", e.getMessage() ); // 발생한 인셉션 메시지
        errorResponse.put("message", message); // 전달 메시지
        
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        
        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(jsonResponse);
        
    } // ==== end of setResponse ====

    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    	
    	String path = request.getRequestURI();
    	boolean chk = false; 
    			
    	if (PathRequest.toStaticResources().atCommonLocations().matches(request)) {
            return true; 
        }
    	
    	for (String permitPath : permitRequestRegistry.getPermitRequests()) {
    		
    		if(pathMatcher.match(permitPath, path)) {
    			chk = true;
    			break;
    		}
    	} // end of for
  	
    	return chk;
    } // ==== end of shouldNotFilter ====
    
} // end of class
