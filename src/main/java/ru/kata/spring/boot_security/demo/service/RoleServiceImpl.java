package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.HashSet;
import java.util.Set;


@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Role getRoleAdmin() {
        return roleDao.findByName("ROLE_ADMIN");
    }

    public Role getRoleUser() {
        return roleDao.findByName("ROLE_USER");
    }

    public Set<Role> getAllRole() {
        return new HashSet<>(roleDao.findAll());
    }
}
