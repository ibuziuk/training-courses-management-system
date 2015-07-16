package org.exadel.training.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "trainingFeedback")
public class EmployeeFeedback {

    @NotEmpty
    private long userID;

    @NotEmpty
    private long trainerID;

    @Column(length = 20)
    private String date;

    private boolean presence;

    private int attitudeToLearning;

    private boolean asks;

    private int communication;

    private boolean interest;

    private boolean focusedOnResults;

    @Column(length = 4500)
    private String text;

    private int level;

    @Column(length = 45)
    private String mark;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPresence() {
        return presence;
    }

    public void setPresence(boolean presence) {
        this.presence = presence;
    }

    public int getAttitudeToLearning() {
        return attitudeToLearning;
    }

    public void setAttitudeToLearning(int attitudeToLearning) {
        this.attitudeToLearning = attitudeToLearning;
    }

    public boolean isAsks() {
        return asks;
    }

    public void setAsks(boolean asks) {
        this.asks = asks;
    }

    public int getCommunication() {
        return communication;
    }

    public void setCommunication(int communication) {
        this.communication = communication;
    }

    public boolean isInterest() {
        return interest;
    }

    public void setInterest(boolean interest) {
        this.interest = interest;
    }

    public boolean isFocusedOnResults() {
        return focusedOnResults;
    }

    public void setFocusedOnResults(boolean focusedOnResults) {
        this.focusedOnResults = focusedOnResults;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
