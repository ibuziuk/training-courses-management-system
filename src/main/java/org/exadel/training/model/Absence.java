package org.exadel.training.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "absence")
public class Absence {

    @NotEmpty
    private long userId;

    @NotEmpty
    private long trainerId;

    @Column(length = 4500)
    private String reasonText;

    private boolean isReasonable;

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

    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    public boolean isReasonable() {
        return isReasonable;
    }

    public void setIsReasonable(boolean isReasonable) {
        this.isReasonable = isReasonable;
    }
}