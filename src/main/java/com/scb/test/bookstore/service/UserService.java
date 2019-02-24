package com.scb.test.bookstore.service;

import com.scb.test.bookstore.config.JwtTokenUtil;
import com.scb.test.bookstore.domain.User;
import com.scb.test.bookstore.exceptions.ResourceConflictException;
import com.scb.test.bookstore.exceptions.ResourceNotFoundException;
import com.scb.test.bookstore.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service(value = "userService")
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User not found for username - %s" , username)));
    }

    @Transactional
    public void save(User user) {
        user = repository.save(user);
        logger.info(String.format("Create successful for user. - %s", user.toString()));
    }

    @Transactional
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(JwtTokenUtil.USER_ROLE)));
    }

    public boolean isUsernameExist(String username){
        if(repository.findByUsername(username).isPresent())
            throw new ResourceConflictException(String.format("Username exist in system - %s" , username));

        return true;
    }
}
