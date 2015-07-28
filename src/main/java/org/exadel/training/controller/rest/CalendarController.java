package org.exadel.training.controller.rest;

import org.exadel.training.model.Training;
import org.exadel.training.service.TrainingService;
import org.exadel.training.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CalendarController {
    @Autowired
    TrainingService trainingService;

    @RequestMapping(value = "/rest/calendar/trainer", method = RequestMethod.GET)
    public List<Training> getByTrainer() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trainingService.getTrainingsByTrainer(userDetails.getId());
    }

    @RequestMapping(value = "/rest/calendar/visitor", method = RequestMethod.GET)
    public List<Training> getByVisitor() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trainingService.getTrainingsByVisitor(userDetails.getId());
    }

    @RequestMapping(value = "/q/{trainingId}")
    public String reg(@PathVariable("trainingId") long trainingId){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trainingService.registerForTraining(trainingId, userDetails.getId());
    }

    @RequestMapping(value = "/d/{trainingId}")
    public String del(@PathVariable("trainingId") long trainingId){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trainingService.removeVisitor(trainingId, userDetails.getId());
    }
}
