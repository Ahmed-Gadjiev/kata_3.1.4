package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleDao) {
        this.userService = userService;
        this.roleService = roleDao;
    }

    @GetMapping("/")
    public String allUsers(ModelMap model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/addNewUser")
    public String addNewUser(ModelMap model) {
        User user = new User();
        List<Role> roles = roleService.getAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);

        return "user-info";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") @Valid User user, @Valid String role) {
        if (user.getId() != null) {
            userService.update(user.getId(), user);
        } else {

            if (role.equals("ROLE_ADMIN")){
                user.setRoles(Set.of(
                        roleService.getByRoleName("ADMIN"),
                        roleService.getByRoleName("USER")
                ));
            } else {
                user.setRoles(Set.of(
                        roleService.getByRoleName("USER")
                ));
            }

            userService.save(user);
        }

        return "redirect:/admin/";
    }

    @PostMapping(value = "/deleteUser", params = {"userId"})
    public String deleteUser(@RequestParam("userId") long id) {
        userService.delete(id);

        return "redirect:/admin/";
    }

    @PostMapping(value = "/updateUser", params = {"userId"})
    public String updateUser(@RequestParam("userId") long id, ModelMap model) {
        User user = userService.getById(id);
        List<Role> roles = roleService.getAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "user-info";
    }
}
