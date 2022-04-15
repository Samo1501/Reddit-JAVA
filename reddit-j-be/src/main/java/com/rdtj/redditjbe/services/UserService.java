package com.rdtj.redditjbe.services;

import com.rdtj.redditjbe.domain.User;
import com.rdtj.redditjbe.dtos.*;
import com.rdtj.redditjbe.exception.domain.*;

import java.util.List;

public interface UserService {
    TokenDTO register (UserRegisterReqDTO userRegisterReqDTO) throws UserNotFoundException, UsernameExistsException, EmailExistsException, CredentialWrongFormatException;
    TokenDTO login (UserLoginReqDTO userLoginReqDTO);
    List<User> getAllUsers();
    List<GetAllUsersResDTO> getAllUsersDTO();

    User findUserByUsername (String username);
    User findUserByEmail (String email);
    User getUserFromToken(String token) throws UserNotFoundException;
    OkDTO changePassword(ChangePasswordDTO changePasswordDTO, String token) throws UserNotFoundException, OldPwDoesntMatchException, OldAndNewPwMatchException;
    OkDTO deleteUser(Long id) throws UserNotFoundException;


    GetUserResDTO getUserDTOById(Long id) throws UserNotFoundException;


}
