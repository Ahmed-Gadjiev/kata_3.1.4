package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
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
    public String allUsers(ModelMap model, Principal principal) {
        User authUser = userService.getByUsername(principal.getName());
        System.out.println(authUser);
        User newUser = new User();

        List<User> users = userService.findAll();
        List<Role> roles = roleService.getAll();

        model.addAttribute("users", users);
        model.addAttribute("user", authUser);
        model.addAttribute("newUser", newUser);
        model.addAttribute("roles", roles);
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
    public String saveUser(@ModelAttribute("user") @Valid User user, @Valid String role, @Valid String newRoles) {
//        if (user.getId() != null) {
//            System.out.println("update");
//            System.out.println(user.getRoles());
//
//            String[] rolesStr = newRoles.split(",");
//            Set<Role> roles = new HashSet<>();
//
//            for (String s : rolesStr) {
//                roles.add(roleService.getByRoleName(s));
//            }
//
//            user.setRoles(roles);
//
//            userService.update(user.getId(), user);
//        } else {
//            System.out.println("new");
//            System.out.println(user.getUsername());
//
//            String[] rolesStr = role.split(",");
//            Set<Role> roles = new HashSet<>();
//
//            for (String s : rolesStr) {
//                roles.add(roleService.getByRoleName(s));
//            }
//
//            user.setRoles(roles);
//            userService.save(user);
//        }
//
//        return "redirect:/admin/";

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
