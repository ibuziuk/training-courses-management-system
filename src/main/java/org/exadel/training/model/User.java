package org.exadel.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.exadel.training.validator.Unique;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

import static org.exadel.training.utils.RoleUtil.buildRoleForView;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long userId;

    @NotEmpty()
    @Size(max = 25)
    @Pattern(regexp = "[a-z-A-Z]*")
    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;

    @NotEmpty
    @Size(max = 25)
    @Pattern(regexp = "[a-z-A-Z]*")
    @Column(name = "last_name", nullable = false, length = 25)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @Column(length = 60, nullable = false)
    private String password;

    @NotEmpty
    @Email
    @Unique
    @Column(name = "e_mail", unique = true)
    private String email;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private Set<Training> leads;

    @JsonIgnore
    @ManyToMany(mappedBy = "visitors")
    private Set<Training> trainings;

    @JsonIgnore
    @ManyToMany(mappedBy = "exVisitors")
    private Set<Training> exTrainings;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<TrainingFeedback> trainingFeedbacks;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<EmployeeFeedback> employeeFeedbacks;

    @JsonIgnore
    @OneToMany(mappedBy = "trainer", fetch = FetchType.EAGER)
    private Set<EmployeeFeedback> feedbacksAboutMe;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<WaitingList> waiting;

    @JsonIgnore
    @OneToMany(mappedBy = "trainer")
    private Set<TrainerFeedback> trainerFeedbacks;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<TrainerFeedback> feedbacksAboutTrainers;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Absence> absences;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<AbsenceLesson> lessonAbsences;

    public long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

    public Set<Training> getLeads() {
        return leads;
    }

    public void setLeads(Set<Training> leads) {
        this.leads = leads;
    }

    public String getRoleForView() {
        return buildRoleForView(roles);
    }

    public Set<TrainingFeedback> getTrainingFeedbacks() {
        return trainingFeedbacks;
    }

    public void setTrainingFeedbacks(Set<TrainingFeedback> trainingFeedbacks) {
        this.trainingFeedbacks = trainingFeedbacks;
    }

    public Set<WaitingList> getWaiting() {
        return waiting;
    }

    public void setWaiting(Set<WaitingList> waiting) {
        this.waiting = waiting;
    }

    public Set<Training> getExTrainings() {
        return exTrainings;
    }

    public void setExTrainings(Set<Training> ex_trainings) {
        this.exTrainings = ex_trainings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        if (!login.equals(user.login)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (userId ^ (userId >>> 32));
    }

    public Set<EmployeeFeedback> getEmployeeFeedbacks() {
        return employeeFeedbacks;
    }

    public void setEmployeeFeedbacks(Set<EmployeeFeedback> employeeFeedbacks) {
        this.employeeFeedbacks = employeeFeedbacks;
    }

    public Set<EmployeeFeedback> getFeedbacksAboutMe() {
        return feedbacksAboutMe;
    }

    public void setFeedbacksAboutMe(Set<EmployeeFeedback> feedbacksAboutMe) {
        this.feedbacksAboutMe = feedbacksAboutMe;
    }

    public Set<TrainerFeedback> getTrainerFeedbacks() {
        return trainerFeedbacks;
    }

    public void setTrainerFeedbacks(Set<TrainerFeedback> trainerFeedbacks) {
        this.trainerFeedbacks = trainerFeedbacks;
    }

    public Set<TrainerFeedback> getFeedbacksAboutTrainers() {
        return feedbacksAboutTrainers;
    }

    public void setFeedbacksAboutTrainers(Set<TrainerFeedback> feedbacksAboutTrainers) {
        this.feedbacksAboutTrainers = feedbacksAboutTrainers;
    }

    public Set<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(Set<Absence> absences) {
        this.absences = absences;
    }

    public Set<AbsenceLesson> getLessonAbsences() {
        return lessonAbsences;
    }

    public void setLessonAbsences(Set<AbsenceLesson> lessonAbsences) {
        this.lessonAbsences = lessonAbsences;
    }
}
