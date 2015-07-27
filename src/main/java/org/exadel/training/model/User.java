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

    @Column(unique = true, nullable = false)
    private String login;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(name = "e_mail", unique = true)
    private String email;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @JsonIgnore

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Training> trainings;

    @JsonIgnore
    @OneToMany(mappedBy = "visitor", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<CurrentList> currentLists;

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

    public Set<CurrentList> getCurrentLists() {
        return currentLists;
    }

    public void setCurrentLists(Set<CurrentList> currentLists) {
        this.currentLists = currentLists;
    }

    public String getRoleForView() {
        return buildRoleForView(roles);
    }
}
