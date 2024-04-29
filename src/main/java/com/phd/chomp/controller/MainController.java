package com.phd.chomp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MainController {

    @GetMapping("/join")
    public String joinGet() {
        /*model.addAttribute("memberJoinDto", new MemberJoinDTO());*/

        log.info("MemberController.joinGet() 회원가입 페이지 접근");

        return "member/signup";
    }

    @GetMapping("/login")
    public String loginGet(){

        log.info("MemberController.loginGet() 로그인 처리");

        return "member/login";
    }

    @GetMapping("/index")
    public String main(){
        log.info("index 페이지 접근");

        return "index";
    }

    @GetMapping("/index1")
    public String main1(){
        log.info("index 페이지 접근");

        return "index1";
    }



}
