package com.rdtj.redditjbe.resource;

import com.rdtj.redditjbe.domain.User;
import com.rdtj.redditjbe.dtos.*;
import com.rdtj.redditjbe.exception.domain.*;
import com.rdtj.redditjbe.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping({"/", "/api/v1"})
public class UserResource extends ExceptionHandling {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<TokenDTO> register(@RequestBody UserRegisterReqDTO userRegisterReqDTO) throws ObjectNotFoundException, UsernameExistsException, EmailExistsException, InputWrongFormatException, RequiredDataIncompleteException {
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

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    @GetMapping("/user")
    public ResponseEntity<List<GetAllUsersResDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsersDTO(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<GetUserResDTO> getUser(@PathVariable Long id) throws ObjectNotFoundException {
        return new ResponseEntity<>(userService.getUserDTOById(id), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<OkDTO> deleteUser(@PathVariable Long id) throws ObjectNotFoundException {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

    @PutMapping("/user/change-password")
    public ResponseEntity<OkDTO> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, @RequestHeader("Authorization") String token) throws ObjectNotFoundException, OldPwDoesntMatchException, OldAndNewPwMatchException {
        return new ResponseEntity<>(userService.changePassword(changePasswordDTO, token), HttpStatus.OK);
    }

    @PutMapping("/user/change-username")
    public ResponseEntity<OkDTO> changeUsername(@RequestBody ChangeUsernameDTO changeUsernameDTO, @RequestHeader("Authorization") String token) throws ObjectNotFoundException, OldPwDoesntMatchException, OldAndNewPwMatchException, RequiredDataIncompleteException, UsernameExistsException {
        return new ResponseEntity<>(userService.changeUsername(changeUsernameDTO, token), HttpStatus.OK);
    }

    @GetMapping("/user/user-settings/{id}")
    public ResponseEntity<User> getUserDetails(@PathVariable Long id) throws ObjectNotFoundException {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @GetMapping("/home")
    public String showUser() {
        return "sdfsdf";
    }
}
