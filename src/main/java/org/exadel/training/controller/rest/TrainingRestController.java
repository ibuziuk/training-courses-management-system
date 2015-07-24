package org.exadel.training.controller.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.exadel.training.model.*;
import org.exadel.training.service.*;
import org.exadel.training.utils.TrainingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Map;

@RestController
public class TrainingRestController {
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TrainingTagService trainingTagService;
    @Autowired
    private AudienceService audienceService;

    @RequestMapping(value = "/rest/training", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Training createTraining(HttpEntity<String> httpEntity) {
        String jsonString = httpEntity.getBody();
        JsonObject json= new JsonParser().parse(jsonString).getAsJsonObject();

        //get login from session
        User trainer = null;

        Training training = new Training();
        if (json.get("type")!=null)
            training.setExternalType(json.get("type").getAsBoolean());
        if (json.get("regular")!=null)
            training.setRegular(json.get("regular").getAsBoolean());
        if (json.get("title")!=null)
            training.setTitle(json.get("title").getAsString());
        if (json.get("description")!=null)
            training.setDescription(json.get("description").getAsString());
        if (json.get("type")!=null)
            training.setExternalType(json.get("type").getAsBoolean());
        if (json.get("visitors")!=null)
            training.setMaxVisitorsCount(json.get("visitors").getAsInt());
        if (json.get("duration")!=null)
            training.setDuration(json.get("duration").getAsInt());
        if (json.get("days")!=null)
            training.setDays(json.get("days").getAsString());

        if (json.get("date")!=null)
            training.setDays(json.get("days").getAsString());

        Language language;
        language = languageService.getLanguageByValue(json.get("language").getAsString());
        training.setLanguage(language);

//        Audience audience ;
//        JsonArray jsonArray = json.get("audience").getAsJsonArray();
//        for (JsonElement audienceValue: jsonArray) {
//            audience = audienceService.getAudienceByValue(audienceValue.getAsString());
//            training.setAudience();
//        }

        trainingService.addTraining(training);

        return training;
    }

}