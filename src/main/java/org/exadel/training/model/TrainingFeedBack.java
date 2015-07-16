package org.exadel.training.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "trainingFeedBack")
public class TrainingFeedBack {

    @NotEmpty
    private long userID;

    @NotEmpty
    private long trainerID;

    @Column(length = 4500)
    private String text;

    @Column(length = 20)
    private String date;

    private int understandably;

    private int interestingly;

    private int learn_something_new;

    private int effectiveness;

    private boolean coachDesire;

    @Column(length = 45)
    private String recommending;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(long trainerID) {
        this.trainerID = trainerID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUnderstandably() {
        return understandably;
    }

    public void setUnderstandably(int understandably) {
        this.understandably = understandably;
    }

    public int getInterestingly() {
        return interestingly;
    }

    public void setInterestingly(int interestingly) {
        this.interestingly = interestingly;
    }

    public int getLearn_something_new() {
        return learn_something_new;
    }

    public void setLearn_something_new(int learn_something_new) {
        this.learn_something_new = learn_something_new;
    }

    public int getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(int effectiveness) {
        this.effectiveness = effectiveness;
    }

    public boolean isCoachDesire() {
        return coachDesire;
    }

    public void setCoachDesire(boolean coachDesire) {
        this.coachDesire = coachDesire;
    }

    public String getRecommending() {
        return recommending;
    }

    public void setRecommending(String recommending) {
        this.recommending = recommending;
    }
}
