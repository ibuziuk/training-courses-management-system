package org.exadel.training.controller;

import org.exadel.training.model.Role;
import org.exadel.training.model.User;
import org.exadel.training.service.NotificationService;
import org.exadel.training.service.RoleService;
import org.exadel.training.service.UserService;
import org.exadel.training.utils.GeneratorFactory;
import org.exadel.training.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.exadel.training.utils.GeneratorFactory.translateNameToValid;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allUsers() {
        return "users";
    }

    @RequestMapping(value = "/external/new", method = RequestMethod.GET)
    public String newExternal(Map<String, Object> map) {
        map.put("user", new User());
        return "new-trainer";
    }

    @RequestMapping(value = "/external/new", method = RequestMethod.POST)
    public String addExternal(@Valid @ModelAttribute("user") User external, BindingResult result) {
    	UserValidator userValidator = new UserValidator(userService);
        userValidator.validate(external, result);
    	
    	if (result.hasErrors()) {
            return "new-trainer";
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRole("external"));
        external.setRoles(roles);

        String firstName = external.getFirstName().toLowerCase();
        String lastName = external.getLastName().toLowerCase();
        String email = external.getEmail();

        external.setFirstName(translateNameToValid(firstName));
        external.setLastName(translateNameToValid(lastName));
        external.setEmail(email.toLowerCase());
        external.setLogin(generateLogin(lastName, firstName, external.getEmail()));

        String password = GeneratorFactory.generatePassword(8);
        external.setPassword(bCryptPasswordEncoder.encode(password));

        userService.addUser(external);

        final User finalExternal = external;
        new Thread(() ->
                notificationService.newExternalCreationNotification(finalExternal, password)
        ).start();

        return "redirect:/user/" + external.getUserId();
    }

    @RequestMapping(value = "/{userId:[\\d]+}")
    public String profile(@PathVariable("userId") long userId) {
        if (userService.getUserById(userId) != null) {
            return "user";
        }
        throw new ResourceNotFoundException();
    }

    private String generateLogin(String lastName, String firstName, String email) {
        String login = GeneratorFactory.generateFirstLogin(firstName, lastName);
        if (userService.getUserByLogin(login) == null) {
            return login;
        }
        login = GeneratorFactory.generateSecondLogin(firstName, lastName);
        if (userService.getUserByLogin(login) == null) {
            return login;
        }
        login = GeneratorFactory.generateLoginFromEmail(email);
        if (userService.getUserByLogin(login) != null) {
            return login;
        }
        login = lastName;
        if (userService.getUserByLogin(login) != null) {
            return login;
        }
        return GeneratorFactory.generateRandomLogin(6);
    }
}
