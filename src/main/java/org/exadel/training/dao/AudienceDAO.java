package org.exadel.training.dao;

import org.exadel.training.model.Audience;

import java.util.List;

public interface AudienceDAO {
    void addAudience(Audience audience);
    Audience getAudienceByValue(String value);
    List<Audience> getAllAudiences();
}
