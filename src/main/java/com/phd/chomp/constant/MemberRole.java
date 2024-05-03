package com.phd.chomp.constant;

import lombok.Getter;

@Getter
public enum MemberRole {

    USER(MemberRole.Authority.USER),
    ADMIN(MemberRole.Authority.ADMIN);

    private final String authority;

    MemberRole(String authority){
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority{
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }

    public static MemberRole fromString(String role) {
        switch (role) {
            case "ROLE_USER":
                return USER;
            case "ROLE_ADMIN":
                return ADMIN;
        }

        return null;
    }
}
