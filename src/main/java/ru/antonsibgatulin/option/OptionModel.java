package ru.antonsibgatulin.option;

import javax.persistence.*;

@Entity
@Table(name = "options", schema = "public")
public class OptionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id = null;


    @Column(name = "time")
    public Long time = null;

    @Column(name = "type")
    public Integer type = null;


    @Column(name = "task")
    public String task = null;



    public OptionModel(){}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
