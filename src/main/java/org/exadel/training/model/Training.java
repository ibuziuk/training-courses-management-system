package org.exadel.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.exadel.training.utils.TrainingUtil;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "training_id")
    private long trainingId;

    @NotEmpty
    @Column(length = 255)
    private String title;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private User trainer;

    private Timestamp date;

    @Column(name = "max_visitors_count")
    private int maxVisitorsCount;

    @Column(length = 20)
    private String time;

    private int location;

    private int duration;

    @Column(length = 1000)
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;

    @Column(length = 20)
    private String days;

    private boolean regular;

    @Column(name = "external_type")
    private boolean externalType;

    @Column(name = "is_approved")
    private boolean isApproved;

    @ManyToOne
    @JoinColumn(name = "language_id")
    @JsonIgnore
    private Language language;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "training_audience", joinColumns = {
            @JoinColumn(name = "training_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "audience_id", nullable = false)
            })
    private Set<Audience> audiences = new HashSet<>(0);

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "training_tag", joinColumns = {
            @JoinColumn(name = "training_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", nullable = false)
            })
    private Set<Tag> tags = new HashSet<>(0);

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "training_user", joinColumns = {
            @JoinColumn(name = "training_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "user_id", nullable = false)
            })
    private Set<User> visitors = new HashSet<>(0);

    @JsonIgnore
    @OneToMany(mappedBy = "training", fetch = FetchType.EAGER)
    private Set<WaitingList> waiting;

//    @JsonIgnore
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "training_user_waiting", joinColumns = {
//            @JoinColumn(name = "training_id", nullable = false)},
//            inverseJoinColumns = {@JoinColumn(name = "user_id", nullable = false)
//            })
//    private Set<User> waiting = new HashSet<>(0);

    @OneToMany(mappedBy = "training", fetch = FetchType.EAGER)
    private Set<RegularLesson> lessons = new HashSet<>(0);

    public long getTrainingId() {
        return trainingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
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

    public int getLocation() {
        return location;
    }

    public void setLocation(int place) {
        this.location = place;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public boolean isRegular() {
        return regular;
    }

    public void setRegular(boolean regular) {
        this.regular = regular;
    }

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return trainingId == training.trainingId;
    }

    @Override
    public int hashCode() {
        return (int) (trainingId ^ (trainingId >>> 32));
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getDateOnString() {
        TrainingUtil trainingUtil = new TrainingUtil();
        return trainingUtil.DateToString(this.date);
    }

    public boolean isExternalType() {
        return externalType;
    }

    public void setExternalType(boolean external) {
        this.externalType = external;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<User> getVisitors() {
        return visitors;
    }

    public void setVisitors(Set<User> visitors) {
        this.visitors = visitors;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Audience> getAudiences() {
        return audiences;
    }

    public void setAudiences(Set<Audience> audiences) {
        this.audiences = audiences;
    }

    public Set<RegularLesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<RegularLesson> lessons) {
        this.lessons = lessons;
    }

    public Set<WaitingList> getWaiting() {
        return waiting;
    }

    public void setWaiting(Set<WaitingList> waiting) {
        this.waiting = waiting;
    }

//    public Set<User> getWaiting() {
//        return waiting;
//    }
//
//    public void setWaiting(Set<User> waiting) {
//        this.waiting = waiting;
//    }
}
