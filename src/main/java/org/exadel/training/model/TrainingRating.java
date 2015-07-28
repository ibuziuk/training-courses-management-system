package org.exadel.training.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "training_rating")
public class TrainingRating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "training_rating_id")
    private int id;

    @NotEmpty
    private long userId;

    @NotEmpty
    private long trainerId;

    private int starCount;

    public int getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(long trainerId) {
        this.trainerId = trainerId;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }
}