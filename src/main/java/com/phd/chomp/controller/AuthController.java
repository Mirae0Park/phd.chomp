package com.phd.chomp.controller;

import com.phd.chomp.dto.MemberRequestDto;
import com.phd.chomp.dto.MemberResponseDto;
import com.phd.chomp.dto.TokenDto;
import com.phd.chomp.dto.TokenRequestDto;
import com.phd.chomp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup") // 회원 가입
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto){
        log.info("uid : " + memberRequestDto.getUid());
        log.info("pw : " + memberRequestDto.getPw());

        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login") // 로그인
    public ResponseEntity<TokenDto> login (@RequestBody MemberRequestDto memberRequestDto){
        log.info("Auth Controller Login method.....");

        TokenDto tokenDto = authService.login(memberRequestDto);

        log.info("RefreshToken : " + tokenDto.getRefreshToken());
        log.info("AccessToken : " + tokenDto.getAccessToken());

        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/reissue") // 재발급
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
