package org.exadel.training.controller;

import org.exadel.training.model.Role;
import org.exadel.training.model.User;
import org.exadel.training.service.RoleService;
import org.exadel.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
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

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allUsers() {
       return "users";
    }

    @RequestMapping(value = "/external/new", method = RequestMethod.GET)
    public String newExternal() {
        return "new-trainer";
    }

    @RequestMapping(value = "/external/new", method = RequestMethod.POST)
    public String addExternal(@ModelAttribute("user") User external, BindingResult result, Map<String, Object> map, HttpSession session) {
        if (result.hasErrors()) {
            return "new-trainer";
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRole("external"));
        external.setRoles(roles);
        /*userService.addUser(external);*/
        return "redirect:/user/" + external.getUserId();
    }

    @RequestMapping(value = "/{userId:[\\d]+}")
    public String profile(@PathVariable("userId") long userId) {
        if (userService.getUserById(userId) != null) {
            return "user";
        }
        throw new ResourceNotFoundException();
    }
}
