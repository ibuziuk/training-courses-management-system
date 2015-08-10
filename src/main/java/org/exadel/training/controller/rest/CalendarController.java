package org.exadel.training.controller.rest;

import org.exadel.training.model.CustomUserDetails;
import org.exadel.training.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CalendarController {
    @Autowired
    private TrainingService trainingService;

    @RequestMapping(value = "/rest/calendar", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getCalendar() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = userDetails.getId();
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        map.put("trainings", trainingService.getSomeTrainingOrderBy(id.toString(), "all", 1, Integer.MAX_VALUE, "title", "asc", true));
        return map;
    }

    @RequestMapping(value = "/rest/calendar/sort", method = RequestMethod.GET,
            params = {"pageNumber", "pageSize", "sorting", "order"})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> sorting(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("sorting") String sorting,
            @RequestParam("order") String order) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = new HashMap<>(2);
        Long id = userDetails.getId();
        map.put("id", id);
        map.put("trainings", trainingService.getSomeTrainingOrderBy(id.toString(), "all", pageNumber, pageSize, sorting, order, true));
        return map;
    }

    @RequestMapping(value = "/rest/calendar/search", method = RequestMethod.GET,
            params = {"pageNumber", "pageSize", "searchType", "value"})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> searching(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("searchType") String searchType,
            @RequestParam("value") String value) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = new HashMap<>(2);
        Long id = userDetails.getId();
        String val = null;
        try {
            val = URLDecoder.decode(value, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("id", id);
        map.put("trainings", trainingService.searchTrainings(id.toString(), "all", true, pageNumber, pageSize, searchType, val));
        return map;
    }
}
