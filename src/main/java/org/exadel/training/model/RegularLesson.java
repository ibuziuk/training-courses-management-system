package org.exadel.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

import static org.exadel.training.utils.TrainingUtil.DateAndTimeToString;

@Entity
@Table(name = "regular_lesson")
public class RegularLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lesson_id")
    private long lessonId;

    private Timestamp date;

    private Integer location;

    @Column(length = 20)
    private String time;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @JsonIgnore
    @OneToMany(mappedBy = "lesson")
    private Set<LessonEdit> edits;

    public long getLessonId() {
        return lessonId;
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

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    @JsonIgnore
    public String getDateAndTimeOnString() {
        return DateAndTimeToString(this.date);
    }

    public Set<LessonEdit> getEdits() {
        return edits;
    }

    public void setEdits(Set<LessonEdit> edits) {
        this.edits = edits;
    }
}

