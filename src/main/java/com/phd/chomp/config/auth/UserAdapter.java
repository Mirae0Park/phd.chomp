package com.phd.chomp.config.auth;

import com.phd.chomp.entity.Member;
import lombok.Getter;

@Getter
public class UserAdapter extends CustomUserDetails{

    private Member member;

    public UserAdapter(Member member){
        super(member);
        this.member = member;
    }

}
