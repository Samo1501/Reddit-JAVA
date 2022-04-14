package com.rdtj.redditjbe.domain;

import com.rdtj.redditjbe.constants.Authority;

public enum Role {
    ROLE_USER(Authority.USER_AUTHORITIES),
    ROLE_MODERATOR(Authority.MODERATOR_AUTHORITIES),
    ROLE_ADMIN(Authority.ADMIN_AUTHORITIES),
    ROLE_OWNER(Authority.OWNER_AUTHORITIES);

    private String[] authorities;
    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities(){
        return authorities;
    }
}
