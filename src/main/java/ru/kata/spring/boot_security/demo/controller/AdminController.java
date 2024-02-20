package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
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
        model.addAttribute("user", user);

        return "user-info";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("role") String role) {
//        String role = request.getParameter("role");
        System.out.println(user.getRoles());
        System.out.println(role);
        if (user.getId() != null) {
            userService.update(user.getId(), user);
        } else {
            user.setRoles(List.of(new Role(role)));
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
        model.addAttribute("user", user);

        return "user-info";
    }
}
