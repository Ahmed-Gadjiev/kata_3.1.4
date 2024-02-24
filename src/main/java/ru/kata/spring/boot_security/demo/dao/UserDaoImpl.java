package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    EntityManager em;

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public User getById(Long id) {
        if (id == null) {
            return null;
        }
        return em.find(User.class, id);
    }

    @Override
    public User getByUsername(String username) {
        User name = em.createQuery("SELECT u FROM User u WHERE u.username = :name", User.class)
                .setParameter("name", username)
                .getSingleResult();

        System.out.println(name);

        return name;
    }

    @Override
    public void update(Long id, User newUser) {
        User user = getById(id);
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setAge(newUser.getAge());
        user.setPassword(newUser.getPassword());
        em.persist(user);
    }

    @Override
    public void delete(long id) {
        em.remove(getById(id));
    }

    @Override
    public List<User> getAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}