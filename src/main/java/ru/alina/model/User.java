package ru.alina.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class User extends BaseEntity {

    @NotBlank
    @Size(min = 5, max = 10)
    private String login;

    @NotBlank
    @Size(min = 5, max = 10)
    private String parol;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getParol() {
        return parol;
    }

    public void setParol(String parol) {
        this.parol = parol;
    }

    public User(int id, String login, String parol) {
        super(id);
        this.login = login;
        this.parol = parol;
    }

    public User(String login, String parol) {
        super(null);
        this.login = login;
        this.parol = parol;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", parol='" + parol + '\'' +
                '}';
    }
}
