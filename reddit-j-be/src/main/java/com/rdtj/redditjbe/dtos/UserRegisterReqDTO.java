package com.rdtj.redditjbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterReqDTO {

    private String username;
    private String email;
    private String password;
}
