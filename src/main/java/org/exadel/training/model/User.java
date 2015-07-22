package org.exadel.training.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long userId;

    @NotEmpty
    @Column(length = 25)
    private String name;

    @NotEmpty
    @Column(length = 25)
    private String surname;

    @Column(name = "role_id")
    private int roleId;

    @Column(unique = true)
    private String login;

    @Column(length = 60)
    private String password;

    @Column(name = "e_mail", unique = true)
    private String email;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Training> trainings;

    @OneToMany(mappedBy = "visitor", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<CurrentList> currentLists;

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleID) {
        this.roleId = roleID;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String soname) {
        this.surname = soname;
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
}
