package org.exadel.training.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "audience")
public class Audience {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "audience_id")
    private int id;

    private String value;

    @OneToMany(mappedBy = "audience", fetch = FetchType.EAGER)
    private Set<TrainingAudience> trainingAudiences;

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<TrainingAudience> getTrainingAudiences() {
        return trainingAudiences;
    }

    public void setTrainingAudiences(Set<TrainingAudience> trainingAudiences) {
        this.trainingAudiences = trainingAudiences;
    }
}
