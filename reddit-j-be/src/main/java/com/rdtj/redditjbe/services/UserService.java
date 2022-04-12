package com.rdtj.redditjbe.services;

import com.rdtj.redditjbe.dtos.UserRegisterReqDTO;

public interface UserService {
    String register(UserRegisterReqDTO userRegisterReqDTO);
}
