package com.rdtj.redditjbe.services;

import com.rdtj.redditjbe.dtos.UserRegisterReqDTO;
import com.rdtj.redditjbe.models.User;
import com.rdtj.redditjbe.models.UserRole;
import com.rdtj.redditjbe.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String register(UserRegisterReqDTO userRegisterReqDTO) {
        emailIsValid(userRegisterReqDTO.getEmail());
        Optional<User> optionalUser = userRepo.findByEmail(userRegisterReqDTO.getEmail());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("User with provided email already exists!");
        }
        usernameIsValid(userRegisterReqDTO.getUsername());
        passwordIsValid(userRegisterReqDTO.getPassword());

        String encodedPassword = bCryptPasswordEncoder.encode(userRegisterReqDTO.getPassword());

        User user = new User(userRegisterReqDTO, UserRole.USER);
        user.setPassword(encodedPassword);
        userRepo.save(user);
        System.out.println("user saved");
        return "user saved";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepo.findByUsername(email);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        return optionalUser.orElseThrow(() -> new UsernameNotFoundException("user not found, " + email));
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
            throw new IllegalStateException("Password doesn't meet the requirements!");
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
}
