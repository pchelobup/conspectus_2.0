package ru.alina.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alina.model.Role;
import ru.alina.model.User;
import ru.alina.util.exception.NotFoundException;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.alina.UserData.*;

class UserServiceTest extends ServiceTest {

    @Autowired
    UserService service;

    @Test
    void create() {
        User created = service.create(getNew());
        int newId = created.getId();
        User user = getNew();
        user.setId(newId);
        match(created, user);
        match(service.get(newId), user);
    }

    @Test
    void dubliateLogin() {
        assertThrows(PersistenceException.class,
                () -> service.create(new User(null, USER1.getLogin(), "12345", Role.USER)));
    }

    @Test
    void unsuitedLogin() {
        assertThrows(ConstraintViolationException.class,
                () -> service.create(UNSUITED_LOGIN));
    }

    @Test
    void unsuitedPassword() {
        assertThrows(ConstraintViolationException.class,
                () -> service.create(UNSUITED_PASSWORD));
    }

    @Test
    void update() {
        User updated = getUpdated();
        service.update(updated);
        match(service.get(USER1_ID), updated);
    }

    @Test
    void delete() {
        service.delete(USER1_ID);
        assertThrows(NotFoundException.class,
                () -> service.get(USER1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID));
    }

    @Test
    void get() {
        match(service.get(USER1_ID), USER1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_ID));
    }

    @Test
    void getAll() {
        match(service.getAll(), USERS);
    }

    @Test
    void getByLogin() {
        User actual = service.getByLogin(USER1.getLogin());
        match(actual, USER1);
    }
}