package com.phd.chomp.service;

import com.phd.chomp.dto.MemberRequestDto;
import com.phd.chomp.dto.MemberResponseDto;
import com.phd.chomp.dto.TokenDto;
import com.phd.chomp.entity.Member;
import com.phd.chomp.jwt.JwtFilter;
import com.phd.chomp.jwt.TokenProvider;
import com.phd.chomp.repository.MemberRepository;
import com.phd.chomp.repository.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto){
        if (memberRepository.existsByUid(memberRequestDto.getUid())){
            throw new RuntimeException("이미 가입되어 있는 회원입니다.");
        }

        Member member = memberRequestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    /*@Transactional
    public TokenDto login(MemberRequestDto memberRequestDto){
        // 1. Login Id/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        // authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        log.info("TokenDto : "+ tokenDto);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        log.info("RefreshToken : "+ refreshToken);

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;

    }*/

    /*로그인*/
    @Transactional
    public TokenDto login(MemberRequestDto requestDto, HttpServletResponse response) {

        Optional<Member> optionalMember = memberRepository.findByUid(requestDto.getUid());

        if (optionalMember.isEmpty()) {
            log.warn("회원이 존재하지 않음");
            throw new IllegalArgumentException("회원이 존재하지 않음");
        }

        Member member = optionalMember.get();
        TokenDto tokenDto = tokenProvider.createAccessToken(member.getUid(), member.getMemberRole());

        /*비밀번호 다름.*/
        if (!passwordEncoder.matches(requestDto.getPw(), member.getPw())) {
            log.warn("비밀번호가 일치하지 않습니다.");
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        /*토큰을 쿠키로 발급 및 응답에 추가*/
        Cookie cookie = new Cookie(JwtFilter.AUTHORIZATION_HEADER,
                TokenProvider.createReFreshToken(member.getUid()));

        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setHttpOnly(true);  // httponly 옵션 설정
        cookie.setSecure(true); // 패킷 암호화

        response.addCookie(cookie);

        return tokenDto;
    }

    /*@Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto){
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())){
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getRefreshToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 회원입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())){
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }*/
}
