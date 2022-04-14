package com.rdtj.redditjbe.services;

import com.rdtj.redditjbe.domain.User;
import com.rdtj.redditjbe.dtos.UserRegisterReqDTO;
import com.rdtj.redditjbe.exception.domain.EmailExistsException;
import com.rdtj.redditjbe.exception.domain.UserNotFoundException;
import com.rdtj.redditjbe.exception.domain.UsernameExistsException;

import java.util.List;

public interface UserService {
    User register(UserRegisterReqDTO userRegisterReqDTO) throws UserNotFoundException, UsernameExistsException, EmailExistsException;
    List<User> getUsers();
    User findUserByUsername (String username);
    User findUserByEmail (String email);

}
