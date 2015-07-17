package org.exadel.training.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long trainingId;

    private long trainerId;

    @NotEmpty
    @Column(length = 255)
    private String title;

    @Column(length = 1000)
    private String description;

    private Timestamp date;

    private Timestamp start;

    private Timestamp end;

    @Column(length = 10)
    private String repeat;

    private boolean regular;

    @Column(length = 11)
    private String time;

    private int maxVisitorsCount;

    @Column(length = 500)
    private String audience;

    @Column(length = 50)
    private String location;

    @Column(length = 45)
    private String duration;

    @Column(length = 500)
    private String tags;

    public long getTrainingId() {
        return trainingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(long trainerID) {
        this.trainerId = trainerID;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMaxVisitorsCount() {
        return maxVisitorsCount;
    }

    public void setMaxVisitorsCount(int MAX_count) {
        this.maxVisitorsCount = MAX_count;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String place) {
        this.location = place;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
