package org.exadel.training.dao;

import org.exadel.training.model.Language;

import java.util.List;

public interface LanguageDAO {
    void addLanguage(Language language);

    List<Language> getAllLanguages();
}
