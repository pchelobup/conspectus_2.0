package ru.alina;

import ru.alina.model.Role;
import ru.alina.model.Topic;
import ru.alina.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserData {
    public static final Integer USER1_ID = 1;
    public static final Integer USER2_ID = 2;
    public static final Integer NOT_FOUND_ID = 1000;
    public static final User USER1 = new User(USER1_ID, "User1", "password", Role.USER);
    public static final User USER2 = new User(USER2_ID, "User2", "00007", Role.USER);
    public static final User UNSUITED_LOGIN= new User(null, "l", "passwo", Role.USER);
    public static final User UNSUITED_PASSWORD = new User(null, "login", "password7777777", Role.USER);
    public static final List<User> USERS = List.of(USER1, USER2);

    public static User getNew() {
       return new User(null, "newUser", "newPass", Role.USER);
    }

    public static User getUpdated() {
        return new User(USER1_ID, "updated", "updated", Role.USER);
    }

    public static void match(User actual, User expeted) {
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expeted);
    }

    public static void match(List<User> actual, List<User> expeted) {
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expeted);
    }
}
