package com.rdtj.redditjbe.services;

import com.rdtj.redditjbe.dtos.UserRegisterReqDTO;
import com.rdtj.redditjbe.models.User;
import com.rdtj.redditjbe.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public String register(UserRegisterReqDTO userRegisterReqDTO) {
        User user = new User(userRegisterReqDTO);
        userRepo.save(user);
        System.out.println("user saved");
        return "user saved";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepo.findByUsername(email);

        if (optionalUser.isEmpty()){
            throw new UsernameNotFoundException(email);
        }
        return optionalUser.orElseThrow(() -> new UsernameNotFoundException("user not found, " + email));
    }
}
