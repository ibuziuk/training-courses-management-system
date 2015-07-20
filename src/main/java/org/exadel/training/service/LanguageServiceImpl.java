package org.exadel.training.service;

import org.exadel.training.dao.LanguageDAO;
import org.exadel.training.model.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LanguageServiceImpl implements LanguageService{
    @Autowired
    private LanguageDAO languageDAO;

    @Override
    @Transactional
    public void addLanguage(Language language){
        languageDAO.addLanguage(language);
    }
}
