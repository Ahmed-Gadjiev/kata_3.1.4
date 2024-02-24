package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void save(User user);

    User getById(Long id);

    User getByUsername(String username);

    void delete(long id);

    void update(Long id, User newUser);

    List<User> getAll();

}
