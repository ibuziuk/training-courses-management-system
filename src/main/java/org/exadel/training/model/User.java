package org.exadel.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

import static org.exadel.training.utils.RoleUtil.buildRoleForView;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long userId;

    @NotEmpty
    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;
    
    @NotEmpty
    @Column(name = "last_name", nullable = false, length = 25)
    private String lastName;

    @JsonIgnore
    @Column(unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @Column(length = 60, nullable = false)
    private String password;

    @JsonIgnore
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
    private Set<WaitingList> waiting;

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

    @JsonIgnore
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
}
