package org.exadel.training.dao;

import org.exadel.training.model.Audience;

import java.util.List;

public interface AudienceDAO {
    void addAudience(Audience audience);

    List<Audience> getAllAudience();

    void updateAudience(Audience audience);

    Audience getAudienceByValue(String value);
}
