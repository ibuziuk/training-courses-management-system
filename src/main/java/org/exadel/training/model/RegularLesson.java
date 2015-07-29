package org.exadel.training.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "regular_lesson")
public class RegularLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lesson_id")
    private int lessonId;

    private Timestamp date;

    private Integer location;

    @Column(length = 20)
    private String time;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    public int getLessonId() {
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

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}

