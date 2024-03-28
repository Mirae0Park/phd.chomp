package com.phd.chomp.controller;

import com.phd.chomp.dto.MemberRequestDto;
import com.phd.chomp.dto.MemberResponseDto;
import com.phd.chomp.dto.TokenDto;
import com.phd.chomp.dto.TokenRequestDto;
import com.phd.chomp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup") // 회원가입
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto){
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login") // 로그인
    public ResponseEntity<TokenDto> login (@RequestBody MemberRequestDto memberRequestDto){
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/reissue") // 재발급
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto){
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

}
