package com.rdtj.redditjbe.services;

import com.rdtj.redditjbe.exception.domain.InputWrongFormatException;
import com.rdtj.redditjbe.repositories.UserRepository;
import com.rdtj.redditjbe.utilities.JWTTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.rdtj.redditjbe.constants.UserImplConstant.*;


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

        Assertions.assertEquals(INVALID_FORMAT_EMAIL, thrown.getMessage());
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

        Assertions.assertEquals(INVALID_FORMAT_EMAIL, thrown.getMessage());
    }

    @Test
    void passwordFormatIsValid() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String password = "pW12345?";

        Assertions.assertDoesNotThrow(() -> userService.passwordFormatIsValid(password));
    }

    @Test
    void passwordFormatIsInvalid1NoSpecial() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String password = "pW123456";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.passwordFormatIsValid(password);
        }, "message");

        Assertions.assertEquals(INVALID_FORMAT_PASSWORD, thrown.getMessage());
    }

    @Test
    void passwordFormatIsInvalid2NoUppercase() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String password = "p012345?";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.passwordFormatIsValid(password);
        }, "message");

        Assertions.assertEquals(INVALID_FORMAT_PASSWORD, thrown.getMessage());
    }

    @Test
    void passwordFormatIsInvalid3NoLowercase() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String password = "AB0123456?";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.passwordFormatIsValid(password);
        }, "message");

        Assertions.assertEquals(INVALID_FORMAT_PASSWORD, thrown.getMessage());
    }

    @Test
    void passwordFormatIsInvalid4TooShort() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String password = "pW012?";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.passwordFormatIsValid(password);
        }, "message");

        Assertions.assertEquals(INVALID_FORMAT_PASSWORD, thrown.getMessage());
    }

    @Test
    void passwordFormatIsInvalid5TooLong() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String password = "abcdefghijklmnopRSTUVW0123456789?";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.passwordFormatIsValid(password);
        }, "message");

        Assertions.assertEquals(INVALID_FORMAT_PASSWORD, thrown.getMessage());
    }


}