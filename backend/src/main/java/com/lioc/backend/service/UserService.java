package com.lioc.backend.service;

import com.lioc.backend.model.User;
import com.lioc.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Log4j2
@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(User user) {
        User u = userRepository.findByEmail(user.getEmail());

        if (u == null) {
            log.error("Login attempt with non-existing email");
            throw new NoSuchElementException("No user found with email: " + user.getEmail());
        }

        if (!u.getPassword().equals(user.getPassword())) {
            log.error("Login attempt with incorrect password");
            throw new NoSuchElementException("Incorrect password for user: " + user.getEmail());
        }

        log.info("User logged in successfully");
        return u;
    }

}