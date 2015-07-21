package org.exadel.training.model;

import org.exadel.training.utils.TrainingUtil;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
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

    @Column(length = 500)
    private String audience;

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
    private Language language;

    @OneToMany(mappedBy = "training", fetch = FetchType.EAGER)
    private Set<TrainingAudience> trainingAudiences;

    @OneToMany(mappedBy = "training", fetch = FetchType.EAGER)
    private Set<TrainingTag> trainingTags;

    @OneToMany(mappedBy = "training", fetch = FetchType.EAGER)
    private Set<CurrentList> currentLists;

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

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
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

    public Set<TrainingTag> getTrainingTags() {
        return trainingTags;
    }

    public void setTrainingTags(Set<TrainingTag> trainingTags) {
        this.trainingTags = trainingTags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        if (trainingId != training.trainingId) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (trainingId ^ (trainingId >>> 32));
        return result;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getDateOnString(){
        TrainingUtil trainingUtil = new TrainingUtil();
        return trainingUtil.DateToString(this.date);
    }

    public Set<CurrentList> getCurrentLists() {
        return currentLists;
    }

    public void setCurrentLists(Set<CurrentList> currentLists) {
        this.currentLists = currentLists;
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

    public Set<TrainingAudience> getTrainingAudiences() {
        return trainingAudiences;
    }

    public void setTrainingAudiences(Set<TrainingAudience> trainingAudiences) {
        this.trainingAudiences = trainingAudiences;
    }
}
