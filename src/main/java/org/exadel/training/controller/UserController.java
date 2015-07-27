package org.exadel.training.controller;

import org.exadel.training.model.User;
import org.exadel.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allUsers(Map<String, Object> map) {
        map.put("user", new User());
        map.put("userList", userService.getAllUsers());
        return "users";
    }
}
