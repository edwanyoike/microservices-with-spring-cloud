package com.utility.usersms.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.utility.usersms.models.LoginRequestModel;
import com.utility.usersms.service.UserService;
import com.utility.usersms.shared.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UserService userService;
    private Environment environment;


    public AuthenticationFilter() {
    }

    @Autowired
    public AuthenticationFilter(UserService userService, Environment environment) {
        this.userService = userService;
        this.environment = environment;
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestModel loginRequestModel = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);

            return getAuthenticationManager().
                    authenticate(new UsernamePasswordAuthenticationToken(loginRequestModel.getEmail(), loginRequestModel.getPassword(), new ArrayList<>()));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String username = ((User)authResult.getPrincipal()).getUsername();

        UserDto userDto = userService.getUserByEmail(username);

        String tokenS = environment.getProperty("token.secret");

        String token = Jwts.builder()
                .setSubject(userDto.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(Objects.requireNonNull(environment.getProperty("token.expiration_time")))))
                .signWith(SignatureAlgorithm.HS512,environment.getProperty("token.secret"))
                .compact();

        response.addHeader("token",token);
        response.addHeader("userId",userDto.getUserId());

    }
}

