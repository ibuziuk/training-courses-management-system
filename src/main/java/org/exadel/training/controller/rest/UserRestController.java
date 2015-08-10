package org.exadel.training.controller.rest;

import org.exadel.training.model.CustomUserDetails;
import org.exadel.training.model.Training;
import org.exadel.training.model.User;
import org.exadel.training.service.StatisticsService;
import org.exadel.training.service.UserService;
import org.exadel.training.utils.RoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/user")
public class UserRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/all", method = RequestMethod.GET,
            params = {"pageNumber", "pageSize", "sortType", "order"})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getAllUsers(@RequestParam int pageNumber, @RequestParam int pageSize,
                                           @RequestParam String sortType, @RequestParam String order) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.hasRole(RoleUtil.ROLE_ADMIN)) {
            throw new ResourceNotFoundException();
        }
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
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.hasRole(RoleUtil.ROLE_ADMIN)) {
            throw new ResourceNotFoundException();
        }
        return userService.searchUsers(pageNumber, pageSize, searchType, value);

    }

    @RequestMapping(value = "/{userId}/general", method = RequestMethod.GET)
    public Map<String, Object> getUserGlobalInfo(@PathVariable("userId") long userId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.hasRole(RoleUtil.ROLE_ADMIN)) {
            throw new ResourceNotFoundException();
        }
        User user = userService.getUserById(userId);
        Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("firstName", user.getFirstName());
        userInfo.put("lastName", user.getLastName());
        userInfo.put("login", user.getLogin());
        userInfo.put("email", user.getEmail());
        userInfo.put("role", user.getRoleForView());
        return userInfo;
    }

    @RequestMapping(value = "/{userId}/visited", method = RequestMethod.GET, params = {"from", "to"})
    public List<Training> getUserVisitedInfo(@PathVariable(value = "userId") long userId,
                                             @RequestParam(value = "from") long fromDate,
                                             @RequestParam(value = "to") long toDate) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.hasRole(RoleUtil.ROLE_ADMIN)) {
            throw new ResourceNotFoundException();
        }
        return statisticsService.getVisitedTrainings(userId, fromDate, toDate);
    }

    @RequestMapping(value = "/{userId}/subscribed", method = RequestMethod.GET, params = {"from", "to"})
    public List<Training> getUserSubscribedInfo(@PathVariable(value = "userId") long userId,
                                                @RequestParam(value = "from") long fromDate,
                                                @RequestParam(value = "to") long toDate) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.hasRole(RoleUtil.ROLE_ADMIN)) {
            throw new ResourceNotFoundException();
        }
        return statisticsService.getSubscribedTrainings(userId, fromDate, toDate);
    }

    @RequestMapping(value = "/{userId}/refused", method = RequestMethod.GET, params = {"from", "to"})
    public List<Training> getUserExTrainingsdInfo(@PathVariable(value = "userId") long userId,
                                                  @RequestParam(value = "from") long fromDate,
                                                  @RequestParam(value = "to") long toDate) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.hasRole(RoleUtil.ROLE_ADMIN)) {
            throw new ResourceNotFoundException();
        }
        return statisticsService.getExTrainings(userId, fromDate, toDate);
    }

    @RequestMapping(value = "/{userId}/waitingList", method = RequestMethod.GET, params = {"from", "to"})
    public List<Training> getUserWaitingListInfo(@PathVariable(value = "userId") long userId,
                                                 @RequestParam(value = "from") long fromDate,
                                                 @RequestParam(value = "to") long toDate) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.hasRole(RoleUtil.ROLE_ADMIN)) {
            throw new ResourceNotFoundException();
        }
        return statisticsService.getWaitingList(userId, fromDate, toDate);
    }

    @RequestMapping(value = "/{userId}/feedbacks", method = RequestMethod.GET)
    public List<Object> getUserFeedbacks(@PathVariable(value = "userId") long userId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.hasRole(RoleUtil.ROLE_ADMIN)) {
            throw new ResourceNotFoundException();
        }
        return statisticsService.getEmployeFeedbacks(userId);
    }

    @RequestMapping(value = "/{userId}/trainingsAsTrainer", method = RequestMethod.GET, params = {"from", "to"})
    public List<Training> getTrainingsAsTrainer(@PathVariable(value = "userId") long userId,
                                                @RequestParam(value = "from") long fromDate,
                                                @RequestParam(value = "to") long toDate) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.hasRole(RoleUtil.ROLE_ADMIN)) {
            throw new ResourceNotFoundException();
        }
        return statisticsService.getTrainingsAsTrainer(userId, fromDate, toDate);
    }

    @RequestMapping(value = "/{userId}/weekly", method = RequestMethod.GET)
    public List<Training> getUserWeeklyTrainings(@PathVariable(value = "userId") long userId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();//for security in future
        if (!userDetails.hasRole(RoleUtil.ROLE_ADMIN)) {
            throw new ResourceNotFoundException();
        }
        return statisticsService.getWeeklyTrainings(userId);
    }

    @RequestMapping(value = "/{userId}/absence", method = RequestMethod.GET, params = {"from", "to"})
    public Map<String, Object> getUserAbsence(@PathVariable(value = "userId") long userId,
                                              @RequestParam(value = "from") long fromDate,
                                              @RequestParam(value = "to") long toDate) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.hasRole(RoleUtil.ROLE_ADMIN)) {
            throw new ResourceNotFoundException();
        }
        Map<String, Object> absenceList = new HashMap<String, Object>();
        absenceList.put("absence", statisticsService.getAbsenceList(userId, fromDate, toDate));
        absenceList.put("absenceLesson", statisticsService.getAbsenceLessonsList(userId, fromDate, toDate));
        return absenceList;
    }
}
