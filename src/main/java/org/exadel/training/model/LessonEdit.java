package org.exadel.training.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "lesson_edit")
public class LessonEdit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lesson_edit_id")
    private long id;

    private Timestamp date;

    private Integer location;

    @Column(length = 20)
    private String time;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private RegularLesson lesson;

    @Column(name = "is_approved")
    private Boolean isApproved;

    public long getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public RegularLesson getLesson() {
        return lesson;
    }

    public void setLesson(RegularLesson lesson) {
        this.lesson = lesson;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }
}
