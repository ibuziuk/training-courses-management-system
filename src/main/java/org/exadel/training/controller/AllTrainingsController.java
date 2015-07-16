package org.exadel.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/allTrainings")
public class AllTrainingsController {
    @RequestMapping(method = RequestMethod.GET)
    public String allTrainings() {
        return "all-trainings/all-trainings";
    }
}
