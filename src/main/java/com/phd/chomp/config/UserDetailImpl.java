package com.phd.chomp.config;

import com.phd.chomp.constant.MemberRole;
import com.phd.chomp.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailImpl implements UserDetails {

    private final Member member;
    private final String uid;
    private final String pw;

    public UserDetailImpl(Member member, String uid, String pw) {
        this.member = member;
        this.uid = uid;
        this.pw = pw;
    }

    public Member getMember(){
        return this.member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        MemberRole role = member.getMemberRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.pw;
    }

    @Override
    public String getUsername() {
        return this.uid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
