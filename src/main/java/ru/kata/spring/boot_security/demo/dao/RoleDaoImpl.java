package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    EntityManager em;

    @Override
    public Role getByRoleName(String roleName) {
        return em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", roleName)
                .getSingleResult();
    }

    @Override
    public List<Role> getAll() {
        return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public void save(Role role) {
        em.persist(role);
    }
}
