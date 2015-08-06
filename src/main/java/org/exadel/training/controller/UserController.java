package org.exadel.training.controller;

import org.exadel.training.model.Role;
import org.exadel.training.model.User;
import org.exadel.training.service.RoleService;
import org.exadel.training.service.UserService;
import org.exadel.training.utils.GeneratorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allUsers() {
        return "users";
    }

    @RequestMapping(value = "/external/new", method = RequestMethod.GET)
    public String newExternal() {
        return "new-trainer";
    }

    @RequestMapping(value = "/external/new", method = RequestMethod.POST)
    public String addExternal(@ModelAttribute("user") User external, BindingResult result, Map<String, Object> map) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRole("external"));
        external.setRoles(roles);

        String lastName = external.getLastName().toLowerCase();
        String firstName = external.getFirstName().toLowerCase();
        String login = GeneratorFactory.generateFirstLogin(firstName, lastName);
        login = getLogin(lastName, firstName, external.getEmail(), login);
        external.setLogin(login);

        String password = GeneratorFactory.generatePassword(8);
        external.setPassword(bCryptPasswordEncoder.encode(password));

        userService.addUser(external);

        return "redirect:/user/" + external.getUserId();
    }

    private String getLogin(String lastName, String firstName, String email, String login) {
        if (userService.getUserByLogin(login) != null) {
            login = GeneratorFactory.generateSecondLogin(firstName, lastName);
            if (userService.getUserByLogin(login) != null) {
                login = GeneratorFactory.generateLoginFromEmail(email);
                if (userService.getUserByLogin(login) != null) {
                    login = lastName;
                    if (userService.getUserByLogin(login) != null) {
                        login = firstName;
                    }
                }
            }
        }
        return login;
    }

    @RequestMapping(value = "/{userId:[\\d]+}")
    public String profile(@PathVariable("userId") long userId) {
        if (userService.getUserById(userId) != null) {
            return "user";
        }
        throw new ResourceNotFoundException();
    }
}
