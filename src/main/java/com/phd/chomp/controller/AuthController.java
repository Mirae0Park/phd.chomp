package com.phd.chomp.controller;

import com.phd.chomp.dto.MemberRequestDto;
import com.phd.chomp.entity.Member;
import com.phd.chomp.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginGet(){

        log.info("MemberController.loginGet() 로그인 처리");

        return "member/login";
    }

    @GetMapping("/join")
    public String joinGet(Model model) {
        model.addAttribute("MemberRequestDto", new MemberRequestDto());

        log.info("MemberController.joinGet() 회원가입 페이지 접근");

        return "member/signup";
    }

    @PostMapping("/signup") // 회원 가입
    public String signup(@RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult, Model model){
        log.info("uid : " + memberRequestDto.getUid());
        log.info("pw : " + memberRequestDto.getPw());

        if (bindingResult.hasErrors()){
            return "/auth/join";
        }

        Member member = Member.createMember(memberRequestDto, passwordEncoder);
        memberService.saveMember(member);

        return "index";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {

        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");

        return "member/login";
    }
}
