package com.phd.chomp.jwt;

import com.phd.chomp.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Log4j2
@Component // 개발자가 직접 작성한 Class를 Bean으로 등록
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 100 * 60 * 60 * 24 * 7; // 7일

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey) { // 생성자
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 유저 정보를 가지고 AccessToken, RefreshToken을 생성함
    public TokenDto generateTokenDto(Authentication authentication){
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       // 회원 식별 id
                .claim(AUTHORITIES_KEY, authorities)        // 권한
                .setExpiration(accessTokenExpiresIn)        // 토큰이 만료될 시간
                .signWith(key, SignatureAlgorithm.HS512)    // 비밀키, 암호화 알고리즘 이름
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder() // 토큰 생성
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME)) // 토큰이 만료될 시간
                .signWith(key, SignatureAlgorithm.HS512) // 비밀키, 암호화 알고리즘 이름
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_TYPE) // (전달자) 승인타입
                .accessToken(accessToken) // access 토큰 객체
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime()) // access 토큰 만료될 시간
                .refreshToken(refreshToken) // refresh 토큰 객체
                .build();

    }

    // JWT 토큰을 복호화하여 토큰에 들어 있는 정보를 꺼냄
    public Authentication getAuthentication(String accessToken){
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection < ? extends  GrantedAuthority > authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증
    public boolean validateToken(String token) {
        try { // Jwts 클래스로 비밀키를 전달하고 토큰으로 클레임을 만들 수 있다면 true 반환
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;

    }

    // Token 복호화 및 예외 발생(토큰 만료, 시그니처 오류) 시 Claims 객체가 만들어지지 않음
    private Claims parseClaims(String accessToken){
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}