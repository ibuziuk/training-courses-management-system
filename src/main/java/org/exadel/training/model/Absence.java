package org.exadel.training.model;

import javax.persistence.*;

@Entity
@Table(name = "absence")
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "absence_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Training training;

    @Column(name = "reason_text", length = 4500)

    private String reasonText;

    @Column(name = "is_reasonable")
    private Boolean isReasonable;

    public int getId() {
        return id;
    }

    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    public Boolean isReasonable() {
        return isReasonable;
    }

    public void setIsReasonable(Boolean isReasonable) {
        this.isReasonable = isReasonable;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}