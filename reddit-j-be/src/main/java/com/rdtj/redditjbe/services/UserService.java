package com.rdtj.redditjbe.services;

import com.rdtj.redditjbe.domain.User;
import com.rdtj.redditjbe.dtos.*;
import com.rdtj.redditjbe.exception.domain.*;

import java.util.List;

public interface UserService {
    TokenDTO register (UserRegisterReqDTO userRegisterReqDTO) throws ObjectNotFoundException, UsernameExistsException, EmailExistsException, InputWrongFormatException, RequiredDataIncompleteException;
    TokenDTO login (UserLoginReqDTO userLoginReqDTO);
    List<User> getAllUsers();
    List<GetAllUsersResDTO> getAllUsersDTO();

    User findUserByUsername (String username);
    User findUserByEmail (String email);
    User getUserFromToken(String token) throws ObjectNotFoundException;
    OkDTO changePassword(ChangePasswordDTO changePasswordDTO, String token) throws ObjectNotFoundException, OldPwDoesntMatchException, OldAndNewPwMatchException;
    OkDTO changeUsername(ChangeUsernameDTO changeUsernameDTO, String token) throws ObjectNotFoundException, RequiredDataIncompleteException, UsernameExistsException;
    void saveUser(User user);
    User findUserById(Long id) throws ObjectNotFoundException;

    OkDTO deleteUser(Long id) throws ObjectNotFoundException;


    GetUserResDTO getUserDTOById(Long id) throws ObjectNotFoundException;


}
