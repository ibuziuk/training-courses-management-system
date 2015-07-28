package org.exadel.training.controller.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.exadel.training.model.*;
import org.exadel.training.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private AudienceService audienceService;

    @RequestMapping(value = "/rest/training", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Training createTraining(HttpEntity<String> httpEntity) {
        String jsonString = httpEntity.getBody();
        JsonObject json= new JsonParser().parse(jsonString).getAsJsonObject();

        Training training = new Training();
        if (json.get("title")!=null)
            training.setTitle(json.get("title").getAsString());
        //here get user from sesion
        User trainer = null;

        if (json.get("visitors")!=null)
            training.setMaxVisitorsCount(json.get("visitors").getAsInt());

        if (json.get("duration")!=null)
            training.setDuration(json.get("duration").getAsInt());

        if (json.get("description")!=null)
            training.setDescription(json.get("description").getAsString());

        if (json.get("regular")!=null)
            training.setRegular(json.get("regular").getAsBoolean());

        if (json.get("type")!=null)
            training.setExternalType(json.get("type").getAsBoolean());

        if (json.get("approved")!=null)
            training.setApproved(json.get("approved").getAsBoolean());
        else
            training.setApproved(false);

        Language language = null;
        String languageValue = json.get("language").getAsString();
        if (languageValue!=null)
            language = languageService.getLanguageByValue(languageValue);
        training.setLanguage(language);

        Set<Audience> audiences = training.getAudiences();
        Audience audience = null;
        JsonArray jsonArray = json.get("audience").getAsJsonArray();
        for (JsonElement audienceValue: jsonArray) {
            audience = audienceService.getAudienceByValue(audienceValue.getAsString());
            if (audience!=null)
                audiences.add(audience);
        }
        training.setAudiences(audiences);

        Set<Tag> tags = training.getTags();
        Tag tag = null;
        jsonArray = json.get("tags").getAsJsonArray();
        for (JsonElement tagValue: jsonArray) {
            tag = tagService.getTagByName(tagValue.getAsString());
            if (tag!=null)
                tags.add(tag);
        }


        if (training.isRegular()) {
            ;
        } else {
            if (json.get("rooms")!=null)
                training.setLocation(json.get("rooms").getAsJsonArray().get(0).getAsInt());

            String dateString = null;
            if (json.get("date")!= null)
                dateString = json.get("date").getAsString();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Date parsedDate = dateFormat.parse(dateString);
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                training.setDate(timestamp);
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }

        trainingService.addTraining(training);

        return training;
    }

}