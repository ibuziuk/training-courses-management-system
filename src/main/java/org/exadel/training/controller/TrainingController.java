package org.exadel.training.controller;

import org.exadel.training.model.CustomUserDetails;
import org.exadel.training.model.Training;
import org.exadel.training.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.exadel.training.utils.RoleUtil.ROLE_EXTERNAL;

@Controller
@RequestMapping("/training")
public class TrainingController {
    @Autowired
    private TrainingService trainingService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allTrainings() {
        return "all-trainings";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newTraining() {
        return "new-training";
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public String myTraining() {
        return "my-trainings";
    }

    @RequestMapping(value = "/{trainingId:[\\d]+}")
    public String profile(@PathVariable("trainingId") long trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        if (training != null) {
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userDetails.hasRole(ROLE_EXTERNAL) && training.getTrainer().getUserId() != userDetails.getId()) {
                throw new AccessDeniedException("Access denied");
            }
            return "training";
        }
        throw new ResourceNotFoundException();
    }
}
