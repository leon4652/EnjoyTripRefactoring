package com.sas.refactoring.user.controller;

import com.sas.refactoring.user.dto.UserDto;
import com.sas.refactoring.user.service.UserService;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody List<String> data) throws Exception {
        return userService.checkLogin(data);
    }

    @PostMapping("/register")
    public void register(@RequestBody List<String> data) throws Exception {
        userService.registerUser(data);
    }

    @PostMapping("/checkduplicate")
    public boolean checkDuplicate(@RequestBody List<String> data) throws Exception {
        return userService.checkDuplicate(data);
    }


}
