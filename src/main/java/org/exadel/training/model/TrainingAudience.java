package org.exadel.training.model;

import javax.persistence.*;

@Entity
@Table(name = "training_audience")
public class TrainingAudience {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "training_audience_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @ManyToOne
    @JoinColumn(name = "audience_id")
    private Audience audience;

    public long getId() {
        return id;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }
}
