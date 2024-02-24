package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleService {
    private final RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Role getByRoleName(String roleName) {
        return roleDao.getByRoleName(roleName);
    }

    public List<Role> getAll() {
        return roleDao.getAll();
    }

    @Transactional
    public void save(Role role) {
        roleDao.save(role);
    }
}
