package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.utils.RoleErrorResponsible;
import ru.kata.spring.boot_security.demo.utils.RoleNotFoundException;
import ru.kata.spring.boot_security.demo.utils.UserErrorResponsible;
import ru.kata.spring.boot_security.demo.utils.UserNotFoundException;

import java.util.Set;

@RestController
@RequestMapping("/roles")
public class RoleRestController {
    private final RoleService roleService;

    @Autowired
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public Set<Role> getAllRoles() {
        return roleService.getAllRole();
    }

    @GetMapping("/{id}")
    public Role getRole(@PathVariable("id") Long id) {
        return roleService.findById(id);
    }

    @ExceptionHandler
    private ResponseEntity<RoleErrorResponsible> handleException(RoleNotFoundException e) {
        RoleErrorResponsible roleErrorResponsible = new RoleErrorResponsible(
                "User not found",
                System.currentTimeMillis());
        return new ResponseEntity<>(roleErrorResponsible, HttpStatus.NOT_FOUND);
    }
}
