package com.rdtj.redditjbe.services;

import com.rdtj.redditjbe.domain.Role;
import com.rdtj.redditjbe.domain.User;
import com.rdtj.redditjbe.domain.UserPrincipal;
import com.rdtj.redditjbe.dtos.TokenDTO;
import com.rdtj.redditjbe.dtos.UserLoginReqDTO;
import com.rdtj.redditjbe.dtos.UserRegisterReqDTO;
import com.rdtj.redditjbe.exception.domain.EmailExistsException;
import com.rdtj.redditjbe.exception.domain.UserNotFoundException;
import com.rdtj.redditjbe.exception.domain.UsernameExistsException;
import com.rdtj.redditjbe.repositories.UserRepository;
import com.rdtj.redditjbe.utilities.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.rdtj.redditjbe.constants.UserImplConstant.*;

@Service
@Transactional
@Qualifier("userDetailsService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()) {
            System.out.println("Login email not found not found by email " + email);
            throw new UsernameNotFoundException("Login email not found not found by email " + email);
        } else {
            userRepository.save(optionalUser.get());
            UserPrincipal userPrincipal = new UserPrincipal(optionalUser.get());
            System.out.println("Returning found user by username " + email);
            return userPrincipal;
        }
    }

    @Override
    public TokenDTO register(UserRegisterReqDTO userRegisterReqDTO) throws UserNotFoundException, UsernameExistsException, EmailExistsException {
        validateNewUsernameAndEmail("", userRegisterReqDTO.getUsername(), userRegisterReqDTO.getEmail());
        System.out.println(userRegisterReqDTO.getPassword());
        User user = new User();
        user.setUsername(userRegisterReqDTO.getUsername());
        user.setEmail(userRegisterReqDTO.getEmail());
        user.setPassword(encodePassword(userRegisterReqDTO.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setAuthorities(Role.ROLE_USER.getAuthorities());
        user.setEnabled(true);
        user.setNotLocked(true);
        userRepository.save(user);

        return new TokenDTO(jwtTokenProvider.generateJwtToken(new UserPrincipal(user)));
    }

    @Override
    public TokenDTO login(UserLoginReqDTO userLoginReqDTO) {
        User loginUser = findUserByEmail(userLoginReqDTO.getEmail());

        return new TokenDTO(jwtTokenProvider.generateJwtToken(new UserPrincipal(loginUser)));
    }


    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, EmailExistsException, UsernameExistsException {
        Optional<User> optionalUserByUsername = userRepository.findUserByUsername(newUsername);
        Optional<User> optionalUserByEmail = userRepository.findUserByEmail(newEmail);

        if (!currentUsername.isBlank()) {
            Optional<User> currentUserByUsername = userRepository.findUserByUsername(currentUsername);
            if (currentUserByUsername.isEmpty()) {
                throw new UsernameNotFoundException(USER_BY_USERNAME_NOT_FOUND);
            }
            if (optionalUserByUsername.isPresent() && !currentUserByUsername.get().getId().equals(optionalUserByUsername.get().getId())) {
                throw new UsernameExistsException(USERNAME_ALREADY_IN_USE);
            }
            if (optionalUserByEmail.isPresent() && !currentUserByUsername.get().getId().equals(optionalUserByEmail.get().getId())) {
                throw new EmailExistsException(EMAIL_ALREADY_IN_USE);
            }
            return currentUserByUsername.get();
        } else {
            if (optionalUserByUsername.isPresent()) {
                throw new UsernameExistsException(USER_BY_USERNAME_FOUND);
            }
            if (optionalUserByEmail.isPresent()) {
                throw new EmailExistsException(EMAIL_ALREADY_IN_USE);
            }
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).get();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).get();
    }

    private boolean emailIsValid(String email) {
        Pattern patternEmail = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
        if (!patternEmail.matcher(email).matches()) {
            throw new IllegalStateException("Email has invalid format!");
        }
        return patternEmail.matcher(email).matches();
    }

    private boolean passwordIsValid(String password) {
        Pattern patternPw = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*.[ -/:-@\\[-`{-~]).{8,30}$");
        if (!patternPw.matcher(password).matches()) {
            throw new IllegalStateException("Password doesn't meet the requirements (English letters only, length between 8-30, at least: 1 lowercase, 1 uppercase, 1 number, 1 special character)");
        }
        return patternPw.matcher(password).matches();
    }

    private boolean usernameIsValid(String username) {
        Pattern patternUsername = Pattern.compile("^[a-zA-Z0-9.-_$@*!]{3,30}$");
        if (!patternUsername.matcher(username).matches()) {
            throw new IllegalStateException("The username has invalid length or contains forbidden characters. Also make sure it doesn't begin with (or include) any empty space(s).");
        }
        return patternUsername.matcher(username).matches();
    }

    private boolean passwordsMatch(String pwRaw, String pwEncoded) {
        return bCryptPasswordEncoder.matches(pwRaw, pwEncoded);
    }


}
