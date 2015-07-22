package org.exadel.training.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private int tagId;

    @Column(length = 45)
    private String name;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE)
    private Set<TrainingTag> trainingTags;

    private String color;

    public int getTagId() {
        return tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TrainingTag> getTrainingTags() {
        return trainingTags;
    }

    public void setTrainingTags(Set<TrainingTag> trainingTags) {
        this.trainingTags = trainingTags;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
