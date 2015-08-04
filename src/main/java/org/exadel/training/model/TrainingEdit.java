package org.exadel.training.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "training_edit")
public class TrainingEdit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "training_edit_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @Column(length = 255)
    private String title;

    private Timestamp date;

    @Column(name = "max_visitors_count")
    private Integer maxVisitorsCount;

    @Column(length = 255)
    private String time;

    private Integer duration;

    @Column(length = 1000)
    private String description;

    @Column(name = "is_approved")
    private Boolean isApproved;

    public long getId() {
        return id;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMaxVisitorsCount() {
        return maxVisitorsCount;
    }

    public void setMaxVisitorsCount(Integer maxVisitorsCount) {
        this.maxVisitorsCount = maxVisitorsCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
