package org.exadel.training.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "current_list")
public class CurrentList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "current_list_id")
    private int id;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User visitor;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    public int getId() {
        return id;
    }

    public User getVisitor() {
        return visitor;
    }

    public void setVisitor(User visitor) {
        this.visitor = visitor;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}