package org.exadel.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/newTraining")
public class NewTrainingController {
    @RequestMapping(method = RequestMethod.GET)
    public String newTraining() {
        return "newTraining";
    }
}
