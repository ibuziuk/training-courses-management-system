package org.exadel.training.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long trainingId;

    @NotEmpty
    @Column(length = 500)
    private String name;

    private long trainerId;

    @Column(length = 20)
    private String date;

    @Column(length = 20)
    private String time;

    private int MAX_count;

    @Column(length = 500)
    private String audience;

    @Column(length = 50)
    private String place;

    @Column(length = 45)
    private String duration;

    @Column(length = 500)
    private String tags;

    public long getTrainingId() {
        return trainingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(long trainerId) {
        this.trainerId = trainerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMAX_count() {
        return MAX_count;
    }

    public void setMAX_count(int MAX_count) {
        this.MAX_count = MAX_count;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
