package org.exadel.training.service;

import org.exadel.training.model.Audience;

import java.util.List;

public interface AudienceService {
    void addAudience(Audience audience);
    Audience getAudienceByValue(String value);
    List<Audience> getAllAudiences();
}
