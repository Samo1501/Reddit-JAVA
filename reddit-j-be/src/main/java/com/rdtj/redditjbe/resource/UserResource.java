package com.rdtj.redditjbe.resource;

import com.rdtj.redditjbe.domain.User;
import com.rdtj.redditjbe.domain.UserPrincipal;
import com.rdtj.redditjbe.dtos.UserLoginReqDTO;
import com.rdtj.redditjbe.dtos.UserRegisterReqDTO;
import com.rdtj.redditjbe.exception.domain.EmailExistsException;
import com.rdtj.redditjbe.exception.domain.ExceptionHandling;
import com.rdtj.redditjbe.exception.domain.UserNotFoundException;
import com.rdtj.redditjbe.exception.domain.UsernameExistsException;
import com.rdtj.redditjbe.services.UserService;
import com.rdtj.redditjbe.utilities.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static com.rdtj.redditjbe.constants.SecurityConstant.JWT_TOKEN_HEADER;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/", "/api/v1"})
public class UserResource extends ExceptionHandling {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping("/auth/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterReqDTO userRegisterReqDTO) throws UserNotFoundException, UsernameExistsException, EmailExistsException {
        return new ResponseEntity<>(userService.register(userRegisterReqDTO), HttpStatus.CREATED);
    }

    @PostMapping("/auth/log-in")
    public ResponseEntity<User> login(@RequestBody UserLoginReqDTO userLoginReqDTO) {
        authenticate(userLoginReqDTO.getEmail(), userLoginReqDTO.getPassword());
        User loginUser = userService.findUserByEmail(userLoginReqDTO.getEmail());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }

    private void authenticate(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable Long id) {
        System.out.println();
        return null;
    }

    @GetMapping("/home")
    public String showUser() {
        return "";
    }
}
