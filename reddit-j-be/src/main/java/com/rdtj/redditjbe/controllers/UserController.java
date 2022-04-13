package com.rdtj.redditjbe.controllers;

import com.rdtj.redditjbe.dtos.UserLoginReqDTO;
import com.rdtj.redditjbe.dtos.UserRegisterReqDTO;
import com.rdtj.redditjbe.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {


    private final UserService userService;

    @PostMapping("/auth/register")
    public String register(@RequestBody UserRegisterReqDTO userRegisterReqDTO) {
        return userService.register(userRegisterReqDTO);
    }

    @PostMapping("/auth/log-in")
    public String login(@RequestBody UserLoginReqDTO userLoginReqDTO) {
        return userService.login(userLoginReqDTO);
    }

    @GetMapping ("/user/{id}")
    public String getUser(@PathVariable Long id) {
        return "shows user";
    }
}
