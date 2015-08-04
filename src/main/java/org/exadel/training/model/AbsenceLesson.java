package org.exadel.training.model;

import javax.persistence.*;

@Entity
@Table(name = "absence_lesson")
public class AbsenceLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "absence_lesson_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private RegularLesson lesson;

    @Column(name = "reason_text", length = 4500)
    private String reasonText;

    @Column(name = "is_reasonable")
    private Boolean isReasonable;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RegularLesson getLesson() {
        return lesson;
    }

    public void setLesson(RegularLesson lesson) {
        this.lesson = lesson;
    }

    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    public Boolean getIsReasonable() {
        return isReasonable;
    }

    public void setIsReasonable(Boolean isReasonable) {
        this.isReasonable = isReasonable;
    }
}
