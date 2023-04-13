package ru.antonsibgatulin.taskofoption;


import javax.persistence.*;

@Entity
@Table(name = "taskofoption",schema = "public")
public class TaskOfOptionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id = null;

    @Column(name = "id_option")
    public Integer id_option = null;


    @Column(name = "id_task")
    public Integer id_task = null;


    public TaskOfOptionModel(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_option() {
        return id_option;
    }

    public void setId_option(Integer id_option) {
        this.id_option = id_option;
    }

    public Integer getId_task() {
        return id_task;
    }

    public void setId_task(Integer id_task) {
        this.id_task = id_task;
    }

    @Override
    public String toString(){
        return "[TASKOFOPTIONMODEL]: "+id+" "+getId_option()+" "+getId_task();
    }
}
