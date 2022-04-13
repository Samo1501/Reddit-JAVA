package com.rdtj.redditjbe.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdtj.redditjbe.dtos.UserLoginReqDTO;
import com.rdtj.redditjbe.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtUtil /*extends UsernamePasswordAuthenticationFilter*/ {

//    private final AuthenticationManager authenticationManager;

    private Algorithm algorithm = Algorithm.HMAC256(System.getenv("access_token_secret"));

//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//
//        try {
//            UserLoginReqDTO authenticationRequest = new ObjectMapper()
//                    .readValue(request.getInputStream(), UserLoginReqDTO.class);
//            Authentication authenticate = new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword());
//            return authenticate;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public String createToken(User user) {
        String token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 5000))
                .sign(algorithm);
        return token;
    }

    public void validateToken(String token, User user) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withSubject(user.getEmail())
                .build();
        jwtVerifier.verify(token);
    }
}
