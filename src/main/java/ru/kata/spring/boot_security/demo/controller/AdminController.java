package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleDao) {
        this.userService = userService;
        this.roleService = roleDao;
    }

    @PostMapping("/saveUser")
    public RedirectView saveUser(@ModelAttribute("user") @Valid User user, @Valid String role, @Valid String newRoles) {
        String[] rolesStr = role.split(",");
        Set<Role> roles = new HashSet<>();

        for (String s : rolesStr) {
            roles.add(roleService.getByRoleName(s));
        }

        user.setRoles(roles);

        if (user.getId() != null) {
            userService.update(user.getId(), user);
        } else {
            userService.save(user);
        }

        return new RedirectView("http://localhost:8080/admin/");
    }

    @PostMapping(value = "/deleteUser", params = {"userId"})
    public RedirectView deleteUser(@RequestParam("userId") long id) {
        userService.delete(id);
        return new RedirectView("http://localhost:8080/admin/");
    }
}
