package ru.antonsibgatulin.user;

import org.hibernate.annotations.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users", schema = "public")
public class User {

    public Integer id = null;
    public String login = null;
    public String password = null;
    public Integer typeUser = null;
    public String mail = null;
    public String name = null;
    public String surname = null;
    public Integer code = 0;
    public Integer ban = 0;
    public Long timeofregistration = null;
    public Long timelastonline = null;


    public User() {
    }


    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "typeUser")
    public Integer getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(Integer typeUser) {
        this.typeUser = typeUser;
    }

    @Column(name = "mail")
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "code")
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Column(name = "ban")
    public Integer getBan() {
        return ban;
    }

    public void setBan(Integer ban) {
        this.ban = ban;
    }

    @Column(name = "timeofregistration")
    public Long getTimeofregistration() {
        return timeofregistration;
    }

    public void setTimeofregistration(Long timeofregistration) {
        this.timeofregistration = timeofregistration;
    }

    @Column(name = "timelastonline")
    public Long getTimelastonline() {
        return timelastonline;
    }

    public void setTimelastonline(Long timelastonline) {
        this.timelastonline = timelastonline;
    }
}
