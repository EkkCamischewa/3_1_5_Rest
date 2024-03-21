package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleService {

    Role getRoleAdmin();

    Role getRoleUser();

    Set<Role> getAllRole();

    Role findById(Long id);
}
