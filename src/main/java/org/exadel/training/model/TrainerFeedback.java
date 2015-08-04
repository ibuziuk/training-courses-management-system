package org.exadel.training.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "trainer_feedback")
public class TrainerFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trainer_feedback_id")
    private long id;

    private Boolean understandably;

    @Column(name = "explain_difficult_places")
    private Boolean difficultPlaces;

    @Column(name = "mark_main_point")
    private Boolean mark;

    private Boolean interest;

    private Boolean asks;

    @Column(name = "explain_how_to_use")
    private Boolean howToUse;

    private Boolean creativity;

    private Boolean nice;

    private Boolean patient;

    @Column(name = "high_erudition")
    private Boolean highErudition;

    private Boolean assimilation;

    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private User trainer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public Boolean getUnderstandably() {
        return understandably;
    }

    public void setUnderstandably(Boolean understandably) {
        this.understandably = understandably;
    }

    public Boolean getDifficultPlaces() {
        return difficultPlaces;
    }

    public void setDifficultPlaces(Boolean difficultPlaces) {
        this.difficultPlaces = difficultPlaces;
    }

    public Boolean getMark() {
        return mark;
    }

    public void setMark(Boolean mark) {
        this.mark = mark;
    }

    public Boolean getInterest() {
        return interest;
    }

    public void setInterest(Boolean interest) {
        this.interest = interest;
    }

    public Boolean getAsks() {
        return asks;
    }

    public void setAsks(Boolean asks) {
        this.asks = asks;
    }

    public Boolean getHowToUse() {
        return howToUse;
    }

    public void setHowToUse(Boolean howToUse) {
        this.howToUse = howToUse;
    }

    public Boolean getCreativity() {
        return creativity;
    }

    public void setCreativity(Boolean creativity) {
        this.creativity = creativity;
    }

    public Boolean getNice() {
        return nice;
    }

    public void setNice(Boolean nice) {
        this.nice = nice;
    }

    public Boolean getPatient() {
        return patient;
    }

    public void setPatient(Boolean patient) {
        this.patient = patient;
    }

    public Boolean getHighErudition() {
        return highErudition;
    }

    public void setHighErudition(Boolean highErudition) {
        this.highErudition = highErudition;
    }

    public Boolean getAssimilation() {
        return assimilation;
    }

    public void setAssimilation(Boolean assimilation) {
        this.assimilation = assimilation;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
