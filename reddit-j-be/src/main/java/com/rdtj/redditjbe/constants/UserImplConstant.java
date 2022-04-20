package com.rdtj.redditjbe.constants;

public class UserImplConstant {

    public static final String EMAIL_ALREADY_IN_USE = "Provided email is already in use by someone else.";
    public static final String USERNAME_ALREADY_IN_USE = "Provided username is already in use by someone else.";
    public static final String USER_BY_USERNAME_FOUND = "User already exists.";
    public static final String USER_BY_USERNAME_NOT_FOUND = "User with provided username not found.";
    public static final String USER_BY_ID_NOT_FOUND = "User with provided ID not found.";

    public static final String DATA_INCOMPLETE = "You have not provided all the necessary user information.";
    public static final String INVALID_FORMAT_EMAIL = "Email has invalid format!";
    public static final String INVALID_FORMAT_PASSWORD = "Password doesn't meet the requirements (English letters only, length between 8-30, at least: 1 lowercase, 1 uppercase, 1 number, 1 special character)";
    public static final String INVALID_FORMAT_USERNAME = "The username has invalid length or contains forbidden characters. Also make sure it doesn't begin with (or include) any empty space(s).";
    public static final String OLD_PW_DOESNT_MATCH = "The old password is not correct.";
    public static final String OLD_AND_NEW_PW_MATCH = "The new password can't be the same as the old password.";





}
