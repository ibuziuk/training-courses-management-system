package org.exadel.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "employee_feedback")
public class EmployeeFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_feedback_id")
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private User trainer;

    private Timestamp date;

    private Boolean present;

    private Integer attitudeToLearning;

    private Boolean askQuestions;

    private Integer communication;

    private Boolean interest;

    private Boolean focusedOnResults;

    @Column(length = 4500)
    private String text;

    private Integer level;

    @Column(length = 45)
    private String mark;

    public int getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Boolean isPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    public Integer getAttitudeToLearning() {
        return attitudeToLearning;
    }

    public void setAttitudeToLearning(Integer attitudeToLearning) {
        this.attitudeToLearning = attitudeToLearning;
    }

    public Boolean isAskQuestions() {
        return askQuestions;
    }

    public void setAskQuestions(Boolean askQuestions) {
        this.askQuestions = askQuestions;
    }

    public Integer getCommunication() {
        return communication;
    }

    public void setCommunication(Integer communication) {
        this.communication = communication;
    }

    public Boolean isInterest() {
        return interest;
    }

    public void setInterest(Boolean interest) {
        this.interest = interest;
    }

    public Boolean isFocusedOnResults() {
        return focusedOnResults;
    }

    public void setFocusedOnResults(Boolean focusedOnResults) {
        this.focusedOnResults = focusedOnResults;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
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
