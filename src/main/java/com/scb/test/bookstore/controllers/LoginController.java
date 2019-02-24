package com.scb.test.bookstore.controllers;

import com.scb.test.bookstore.config.JwtTokenUtil;
import com.scb.test.bookstore.domain.User;
import com.scb.test.bookstore.exceptions.ResourceNotFoundException;
import com.scb.test.bookstore.exceptions.ServerInternalErrorException;
import com.scb.test.bookstore.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@Api(value = "login", description = "Operations pertaining to user login")
public class LoginController {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping
    public String login(@ApiParam(required = true)
                      @Valid @RequestBody User user) throws AuthenticationException {
        try {
            User result = userService.findByUsername(user.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            return jwtTokenUtil.generateToken(result);
        }catch (ResourceNotFoundException ex){
            throw new ResourceNotFoundException(ex.getMessage());
        }catch (Exception ex){
            throw new ServerInternalErrorException();
        }
    }
}
