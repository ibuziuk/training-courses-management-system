package org.exadel.training.controller.rest;

import org.exadel.training.model.Audience;
import org.exadel.training.service.AudienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AudienceRestController {
    @Autowired
    private AudienceService audienceService;

    @RequestMapping(value = "/rest/audience", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Audience> getAudience() {
        return audienceService.getAllAudiences();
    }
}
