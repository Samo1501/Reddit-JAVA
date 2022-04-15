package com.rdtj.redditjbe.services;

import com.auth0.jwt.JWT;
import com.rdtj.redditjbe.domain.Role;
import com.rdtj.redditjbe.domain.User;
import com.rdtj.redditjbe.domain.UserPrincipal;
import com.rdtj.redditjbe.dtos.*;
import com.rdtj.redditjbe.exception.domain.*;
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
import java.util.stream.Collectors;

import static com.rdtj.redditjbe.constants.SecurityConstant.TOKEN_PREFIX;
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
    public TokenDTO register(UserRegisterReqDTO userRegisterReqDTO) throws UserNotFoundException, UsernameExistsException, EmailExistsException, CredentialWrongFormatException {
        emailFormatIsValid(userRegisterReqDTO.getEmail());
        usernameFormatIsValid(userRegisterReqDTO.getUsername());
        passwordFormatIsValid(userRegisterReqDTO.getPassword());

        validateNewUsernameAndEmail("", userRegisterReqDTO.getUsername(), userRegisterReqDTO.getEmail());

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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<GetUserResDTO> getAllUsersDTO() {
        List<User> users = getAllUsers();
        return users.stream().map(user -> new GetUserResDTO(user)).collect(Collectors.toList());
    }


    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).get();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).get().;
    }

    @Override
    public User getUserFromToken(String token) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUsername(JWT.decode(token).getSubject());

        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new UserNotFoundException(USER_BY_USERNAME_NOT_FOUND);
    }

    @Override
    public OkDTO changePassword(ChangePasswordDTO changePasswordDTO, String token) throws UserNotFoundException, OldPwDoesntMatchException, OldAndNewPwMatchException {
        User user = getUserFromToken(token.substring(TOKEN_PREFIX.length()));
        if (passwordsMatch(changePasswordDTO.getOld_password(), user.getPassword())){
            if (passwordsMatch(changePasswordDTO.getNew_password(), user.getPassword())){
                throw new OldAndNewPwMatchException(OLD_AND_NEW_PW_MATCH);
            }
            user.setPassword(encodePassword(changePasswordDTO.getNew_password()));
            userRepository.save(user);
            return new OkDTO();
        }
        throw new OldPwDoesntMatchException(OLD_PW_DOESNT_MATCH);
    }

    @Override
    public GetUserResDTO getUserDTOById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findUserById(id);

        if (optionalUser.isPresent()){
            return new GetUserResDTO(optionalUser.get());
        }
       throw new UserNotFoundException(USER_BY_ID_NOT_FOUND);
    }

    private void emailFormatIsValid(String email) throws CredentialWrongFormatException {
        Pattern patternEmail = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
        if (!patternEmail.matcher(email).matches()) {
            throw new CredentialWrongFormatException(INVALID_FORMAT_EMAIL);
        }
    }

    private void passwordFormatIsValid(String password) throws CredentialWrongFormatException {
        Pattern patternPw = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*.[ -/:-@\\[-`{-~]).{8,30}$");
        if (!patternPw.matcher(password).matches()) {
            throw new CredentialWrongFormatException(INVALID_FORMAT_PASSWORD);
        }
    }

    private void usernameFormatIsValid(String username) throws CredentialWrongFormatException {
        Pattern patternUsername = Pattern.compile("^[a-zA-Z0-9.-_$@*!]{3,30}$");
        if (!patternUsername.matcher(username).matches()) {
            throw new CredentialWrongFormatException(INVALID_FORMAT_USERNAME);
        }
    }

    private boolean passwordsMatch(String pwRaw, String pwEncoded) {
        return bCryptPasswordEncoder.matches(pwRaw, pwEncoded);
    }


}
