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

        Assertions.assertDoesNotThrow(() -> userService.validateFormatEmail(email));
    }

    @Test
    void emailFormatIsInvalid1EmptyBefore() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String email = "@gmail.com";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.validateFormatEmail(email);
        }, "message");

        Assertions.assertEquals(INVALID_FORMAT_EMAIL, thrown.getMessage());
    }

    @Test
    void emailFormatIsInvalid2NoAtChar() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String email = "abcgmail.com";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.validateFormatEmail(email);
        }, "message");

        Assertions.assertEquals(INVALID_FORMAT_EMAIL, thrown.getMessage());
    }

    @Test
    void emailFormatIsInvalid2NoStringAfterAtChar() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String email = "abc@.com";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.validateFormatEmail(email);
        }, "message");

        Assertions.assertEquals(INVALID_FORMAT_EMAIL, thrown.getMessage());
    }

    @Test
    void emailFormatIsInvalid3StartsWithEmptySpace() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String email = " abc@gmail.com";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.validateFormatEmail(email);
        }, "message");

        Assertions.assertEquals(INVALID_FORMAT_EMAIL, thrown.getMessage());
    }

    @Test
    void emailFormatIsInvalid3EndsWithEmptySpace() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String email = "abc@gmail.com ";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.validateFormatEmail(email);
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

        Assertions.assertDoesNotThrow(() -> userService.validateFormatPassword(password));
    }

    @Test
    void passwordFormatIsInvalid1NoSpecial() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String password = "pW123456";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.validateFormatPassword(password);
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
            userService.validateFormatPassword(password);
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
            userService.validateFormatPassword(password);
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
            userService.validateFormatPassword(password);
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
            userService.validateFormatPassword(password);
        }, "message");

        Assertions.assertEquals(INVALID_FORMAT_PASSWORD, thrown.getMessage());
    }

    @Test
    void usernameFormatIsValid() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String username = "UsersUsername5";

        Assertions.assertDoesNotThrow(() -> userService.validateFormatUsername(username));
    }

    @Test
    void usernameFormatIsInvalid1EmptySpace() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String username = "UsersUsername5 asdf";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.validateFormatUsername(username);
        }, "message");

        Assertions.assertEquals(INVALID_FORMAT_USERNAME, thrown.getMessage());
    }

    @Test
    void usernameFormatIsInvalid2TooLong() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String username = "UsersUserhfghdfghname5gdfgdgsdfdghdfhggsdgsd";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.validateFormatUsername(username);
        }, "message");

        Assertions.assertEquals(INVALID_FORMAT_USERNAME, thrown.getMessage());
    }

    @Test
    void usernameFormatIsInvalid2TooShort() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String username = "ab";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.validateFormatUsername(username);
        }, "message");

        Assertions.assertEquals(INVALID_FORMAT_USERNAME, thrown.getMessage());
    }

    @Test
    void usernameFormatIsInvalid4SpecialCharsRepeated() {

        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        final JWTTokenProvider jwtTokenProvider = Mockito.mock(JWTTokenProvider.class);

        final UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenProvider);
        String username = "UsersUsername5%^";

        Exception thrown = Assertions.assertThrows(InputWrongFormatException.class, () -> {
            userService.validateFormatUsername(username);
        }, "message");

        Assertions.assertEquals(INVALID_FORMAT_USERNAME, thrown.getMessage());
    }


}