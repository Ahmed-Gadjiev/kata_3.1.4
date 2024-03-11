package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {
    private final UserService userService;
    private final RoleService roleService;

    public ApiController(UserService userService, RoleService roleDao) {
        this.userService = userService;
        this.roleService = roleDao;
    }

    @GetMapping("/allUsers")
    public List<User> allUsers() {
        return userService.getAll();
    }

    @GetMapping("/allRoles")
    public List<Role> allRoles() {
        return roleService.getAll();
    }

    @GetMapping("/user")
    public User getUserByName(Principal principal) {
        return userService.getByUsername(principal.getName());
    }
}
