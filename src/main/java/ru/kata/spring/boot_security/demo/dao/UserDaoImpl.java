package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    EntityManager em;

    final private RoleService roleService;

    public UserDaoImpl(RoleService roleService) {
        this.roleService = roleService;
    }

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
        return em.createQuery("SELECT u FROM User u WHERE u.username = :name", User.class)
                .setParameter("name", username)
                .getSingleResult();
    }

    @Override
    public void update(Long id, User newUser) {
        Set<Role> roles = new HashSet<>();

        for (Role r : newUser.getRoles()) {
            roles.add(roleService.getByRoleName(r.getName()));
        }

        newUser.setRoles(roles);
        em.merge(newUser);
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