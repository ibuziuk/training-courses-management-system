package org.exadel.training.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "currentList")
public class CurrentList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "current_list_id")
    private int id;

    @NotEmpty
    private long userId;

    @NotEmpty
    private long trainingId;

    public int getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }
}