package org.exadel.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "training_feedback")
public class TrainingFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "training_feedback_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @Column(length = 4500)
    private String text;

    private Timestamp date;

    private Integer impression;

    private Integer intelligibility;

    private Integer interest;

    @Column(name = "learn_smth_new")
    private Integer update;

    private Integer effectiveness;

    @Column(name = "trainer_desire")
    private Boolean trainerRecommending;

    private Boolean recommending;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "star_count")
    private Integer starCount;

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getIntelligibility() {
        return intelligibility;
    }

    public void setIntelligibility(Integer intelligibility) {
        this.intelligibility = intelligibility;
    }

    public Integer getInterest() {
        return interest;
    }

    public void setInterest(Integer interest) {
        this.interest = interest;
    }

    public Integer getUpdate() {
        return update;
    }

    public void setUpdate(Integer update) {
        this.update = update;
    }

    public Integer getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(Integer effectiveness) {
        this.effectiveness = effectiveness;
    }

    public Boolean isTrainerRecommending() {
        return trainerRecommending;
    }

    public void setTrainerRecommending(Boolean trainerRecommending) {
        this.trainerRecommending = trainerRecommending;
    }

    public Boolean getRecommending() {
        return recommending;
    }

    public void setRecommending(boolean recommending) {
        this.recommending = recommending;
    }

    public Integer getImpression() {
        return impression;
    }

    public void setImpression(Integer impression) {
        this.impression = impression;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }
}
