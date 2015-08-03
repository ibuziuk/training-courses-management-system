package org.exadel.training.controller.rest;

import org.exadel.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rest/user")
public class UserRestController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET,
            params = {"pageNumber", "pageSize", "sortType", "order"})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getAllUsers(@RequestParam int pageNumber, @RequestParam int pageSize,
                                           @RequestParam String sortType, @RequestParam String order) {
        Map<String, Object> map = new HashMap<>();
        map.put("users", userService.getAllUsers(pageNumber, pageSize, sortType, order));
        map.put("size", userService.getAllUsers().size());
        return map;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET,
            params = {"searchType", "value"})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> findUsers(@RequestParam String searchType, @RequestParam String value) {
        Map<String, Object> map = new HashMap<>();
        map.put("users", null);
        map.put("size", userService.getAllUsers().size());
        return map;
    }
}
