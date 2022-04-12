package com.rdtj.redditjbe.services;

import com.rdtj.redditjbe.dtos.UserLoginReqDTO;
import com.rdtj.redditjbe.dtos.UserRegisterReqDTO;

public interface UserService {
    String register(UserRegisterReqDTO userRegisterReqDTO);
    String login(UserLoginReqDTO userLoginReqDTO);

}
