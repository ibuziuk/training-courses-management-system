package org.exadel.training.controller.rest;

import org.exadel.training.model.User;
import org.exadel.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/user")
public class UserRestController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/search/{filter}/{value}", method = RequestMethod.GET)
    public List<User> searchUsers(@PathVariable("filter") String filter, @PathVariable("value") String value) {
        return null;
    }

    @RequestMapping(value = "/sort", method = RequestMethod.GET)
    public List<User> getSortedUsers() {
        return null;
    }
}
