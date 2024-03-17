package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    private final RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public AdminController(UserService userService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/all")
    public String getAllPeople(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/all";
    }

    @GetMapping("/particular/{id}")
    public String getParticularUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "admin/particular";
    }

    @GetMapping("/new")
    public String addNewUser(@ModelAttribute("user") User user) {
        return "admin/new";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "role1", required = false) boolean adminFlag,
                             @RequestParam(value = "password") String password) {
        Set<Role> userRole = new HashSet<>();

        if (adminFlag) {
            userRole.add(roleService.getRoleAdmin());
        }
        userRole.add(roleService.getRoleUser());

        user.setRoles(userRole);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userService.addNewUser(user);

        return "redirect:/admin/all";
    }

    @GetMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "admin/edit";
    }

    @PatchMapping("/particular/{id}")
    public String editUser(@ModelAttribute("user") User user,
                           @PathVariable("id") Long id,
                           @RequestParam(value = "role1", required = false)
                           boolean adminFlag,
                           @RequestParam(value = "password")
                               String password) {
        Set<Role> userRole = new HashSet<>();
        userRole.add(roleService.getRoleUser());
        if (adminFlag) {
            userRole.add(roleService.getRoleAdmin());
        }
        user.setRoles(userRole);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userService.updateUser(user);
        return "redirect:/admin/all";
    }

    @DeleteMapping("/particular/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/all";
    }

}
