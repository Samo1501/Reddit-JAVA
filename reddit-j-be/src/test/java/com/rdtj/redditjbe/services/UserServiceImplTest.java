package com.rdtj.redditjbe.services;

import com.rdtj.redditjbe.exception.domain.InputWrongFormatException;
import com.rdtj.redditjbe.repositories.UserRepository;
import com.rdtj.redditjbe.utilities.JWTTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


class UserServiceImplTest {


    @Test
    void emailFormatIsValid() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String email = "abc@gmail.com";

        Assertions.assertDoesNotThrow(() -> userService.emailFormatIsValid(email));
    }

    @Test
    void emailFormatIsInvalid1() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String email = "@gmail.com";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.emailFormatIsValid(email);
        }, "message");

        Assertions.assertEquals("Email has invalid format!", thrown.getMessage());
    }

    @Test
    void emailFormatIsInvalid2() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String email = "abcgmail.com";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.emailFormatIsValid(email);
        }, "message");

        Assertions.assertEquals("Email has invalid format!", thrown.getMessage());
    }


}