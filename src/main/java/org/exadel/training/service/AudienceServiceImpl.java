package org.exadel.training.service;

import org.exadel.training.dao.AudienceDAO;
import org.exadel.training.model.Audience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AudienceServiceImpl implements AudienceService{
    @Autowired
    private AudienceDAO audienceDAO;

    @Override
    @Transactional
    public Audience getAudienceByValue(String value) {
        return audienceDAO.getAudienceByValue(value);
    }

    @Override
    @Transactional
    public List<Audience> getAllAudiences() {
        return audienceDAO.getAllAudiences();
    }

    @Override
    @Transactional
    public void addAudience(Audience audience){
        audienceDAO.addAudience(audience);
    }
}
