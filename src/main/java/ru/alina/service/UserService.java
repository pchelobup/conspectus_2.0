package ru.alina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alina.model.User;
import ru.alina.repository.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alina.util.ValidationUtil;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User create(User user) {
        log.info("create {}", user);
        return repository.create(user);
    }

    @Transactional
    public User update(User user) {
        log.info("update {}", user);
        return repository.update(user);
    }

    @Transactional
    public void delete(int id) {
        log.info("delete {}", id);
        ValidationUtil.notFound(repository.delete(id), id, User.class.getSimpleName());
    }

    public User get(int id) {
        log.info("get {}", id);
        return ValidationUtil.notFoundAndReturn(repository.get(id), id, User.class.getSimpleName());
    }

    public List<User> getAll() {
        log.info("getAll");
        return repository.getAll();
    }
}
