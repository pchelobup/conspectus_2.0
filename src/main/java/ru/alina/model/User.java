package ru.alina.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = User.DELETE, query = "DELETE from User u where u.id=?1"),
        @NamedQuery(name = User.GET_BY_LOGIN, query = "select u from User u where u.login=?1")
})


public class User extends BaseEntity {
    public static final String DELETE = "User.delete";
    public static final String GET_BY_LOGIN = "User.getByLogin";

    @NotBlank
    @Size(min = 5, max = 10)
    private String login;

    @NotBlank
    @Size(min = 5, max = 10)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User(Integer id, String login, String password, Role role) {
        this.id=id;
        this.login = login;
        this.password = password;
        this.role = role;
    }


    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
