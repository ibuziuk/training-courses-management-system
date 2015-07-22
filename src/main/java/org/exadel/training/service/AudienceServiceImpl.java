package org.exadel.training.service;

import org.exadel.training.dao.AudienceDAO;
import org.exadel.training.model.Audience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AudienceServiceImpl implements AudienceService{
    @Autowired
    private AudienceDAO audienceDAO;

    @Override
    @Transactional
    public void addAudience(Audience audience){
        audienceDAO.addAudience(audience);
    }
}
