package org.exadel.training.controller.rest;

import org.exadel.training.model.User;
import org.exadel.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/rest/user")
public class UserRestController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET,
            params = {"pageNumber", "pageSize", "sortType", "order"})
    public Map<String, Object> getAllUsers(@RequestParam int pageNumber, @RequestParam int pageSize,
                                           @RequestParam String sortType, @RequestParam String order) {
        Map<String, Object> map = new HashMap<>();
        map.put("users", null); //function(pageNumber, pageSize, sortType, order)
        map.put("size", userService.getAllUsers().size());
        return map;
    }

    /*@RequestMapping(value = "/search/{filter}/{value}", method = RequestMethod.GET)
    public List<User> searchUsers(@PathVariable("filter") String filter, @PathVariable("value") String value) {
        return null;
    }

    @RequestMapping(value = "/sort", method = RequestMethod.GET)
    public List<User> getSortedUsers() {
        return null;
    }*/
}
