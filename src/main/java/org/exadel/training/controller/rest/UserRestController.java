package org.exadel.training.controller.rest;

import org.exadel.training.model.User;
import org.exadel.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
            params = {"pageNumber", "pageSize", "searchType", "value"})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> findUsers(@RequestParam int pageNumber, @RequestParam int pageSize,
                                         @RequestParam String searchType, @RequestParam String value) {
        Map<String, Object> map = new HashMap<>();
        List<User> list = userService.searchUsers(pageNumber, pageSize, searchType, value);
        map.put("users", list);
        map.put("size", list.size());
        return map;
    }
}
