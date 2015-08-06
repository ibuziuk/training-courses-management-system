package org.exadel.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "language_id")
    private int id;

    @NotEmpty
    private String value;

    @JsonIgnore
    @OneToMany(mappedBy = "language")
    private Set<Training> trainings;

    @JsonIgnore
    @OneToMany(mappedBy = "language")
    private Set<TrainingEdit> trainingEdits;

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

    public Set<TrainingEdit> getTrainingEdits() {
        return trainingEdits;
    }

    public void setTrainingEdits(Set<TrainingEdit> trainingEdits) {
        this.trainingEdits = trainingEdits;
    }
}
