package ru.antonsibgatulin.teory;

import javax.persistence.*;

@Entity
@Table(name = "teory")
public class TheoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id = null;

    @Column(name = "name")
    public String name = null;

    @Column(name = "description")
    public String description = null;

    @Column(name = "time")
    public Long time = null;


    public TheoryModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


    public String getDescriptionGene(){
        return description.replaceAll("\n","<div class=\"new-wrap\"></div>").replaceAll("<code>","<textarea>").replaceAll("</code>","</textarea>");
    }

    public String getDescriptionGeneAdmin(){
        return description;//.replaceAll("\n","<div class=\"new-wrap\"></div>");
    }


}
