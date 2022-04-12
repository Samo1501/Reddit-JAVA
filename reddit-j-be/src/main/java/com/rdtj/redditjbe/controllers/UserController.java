package com.rdtj.redditjbe.controllers;

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
        System.out.println(userRegisterReqDTO.getUsername());
        return userService.register(userRegisterReqDTO);
    }

    @GetMapping ("/user/{id}")
    public String getUser(@PathVariable Long id) {
        System.out.println();
        return null;
    }
}
