package org.exadel.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/training")
public class TrainingController {
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allTrainings() {
        return "all-trainings";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newTraining() {
        return "new-training";
    }
}
