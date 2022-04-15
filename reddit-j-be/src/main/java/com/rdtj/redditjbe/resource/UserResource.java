package com.rdtj.redditjbe.resource;

import com.rdtj.redditjbe.domain.User;
import com.rdtj.redditjbe.domain.UserPrincipal;
import com.rdtj.redditjbe.dtos.TokenDTO;
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
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping({"/", "/api/v1"})
public class UserResource extends ExceptionHandling {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<TokenDTO> register(@RequestBody UserRegisterReqDTO userRegisterReqDTO) throws UserNotFoundException, UsernameExistsException, EmailExistsException {
        TokenDTO tokenDTO = userService.register(userRegisterReqDTO);
        return new ResponseEntity<>(tokenDTO, HttpStatus.CREATED);
    }

    @PostMapping("/auth/log-in")
    public ResponseEntity<TokenDTO> login(@RequestBody UserLoginReqDTO userLoginReqDTO) {
        authenticate(userLoginReqDTO.getEmail(), userLoginReqDTO.getPassword());
        TokenDTO tokenDTO = userService.login(userLoginReqDTO);
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }

//    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal, String token) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(JWT_TOKEN_HEADER, token);
//        return headers;
//    }

    private void authenticate(String email, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
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
