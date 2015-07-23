package org.exadel.training.service;

import org.exadel.training.model.Audience;

import java.util.List;

public interface AudienceService {
    void addAudience(Audience audience);

    List<Audience> getAllAudience();

    void updateAudience(Audience audience);
}
