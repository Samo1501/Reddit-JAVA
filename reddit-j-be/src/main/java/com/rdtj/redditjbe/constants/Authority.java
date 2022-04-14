package com.rdtj.redditjbe.constants;

public class Authority {
    public static final String[] USER_AUTHORITIES = {"user:read"};
    public static final String[] MODERATOR_AUTHORITIES = {"user:read", "user:update"};
    public static final String[] ADMIN_AUTHORITIES = {"user:read", "user:update", "user:create"};
    public static final String[] OWNER_AUTHORITIES = {"user:read", "user:update", "user:create", "user:delete"};

}
