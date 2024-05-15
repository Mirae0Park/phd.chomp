package com.phd.chomp.service;

import com.phd.chomp.entity.Member;
import com.phd.chomp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Member saveMember(Member member) {
        Member findId = memberRepository.findWithRoleSetByUid(member.getUid());

        if (findId != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

        return memberRepository.save(member);
    }

    @Override
    public Member getMemberInfo(String uid) {
        return memberRepository.findWithRoleSetByUid(uid);
    }
}
