package org.exadel.training.controller.rest;

import org.exadel.training.model.Tag;
import org.exadel.training.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagRestController {
    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/rest/tag", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> getTags() {
        return tagService.getAllTags();
    }
}
