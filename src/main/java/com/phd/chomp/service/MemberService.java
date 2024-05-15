package com.phd.chomp.service;

import com.phd.chomp.entity.Member;

public interface MemberService {

    // 회원가입
    Member saveMember(Member member);

    // 주문자 정보
    public Member getMemberInfo(String uid);

}
