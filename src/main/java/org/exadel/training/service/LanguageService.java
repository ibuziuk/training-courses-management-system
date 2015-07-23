package org.exadel.training.service;

import org.exadel.training.model.Language;

import java.util.List;

public interface LanguageService {
    void addLanguage(Language language);

    List<Language> getAllLanguages();
}
