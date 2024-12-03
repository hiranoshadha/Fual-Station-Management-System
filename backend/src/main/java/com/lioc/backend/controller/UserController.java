package com.lioc.backend.controller;

import com.lioc.backend.model.User;
import com.lioc.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {

    private UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<User> userLogin(@RequestBody User user) {
        return ResponseEntity.ok(userService.login(user));
    }

}
