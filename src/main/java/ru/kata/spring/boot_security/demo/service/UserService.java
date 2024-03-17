package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    User getById(Long id);

    void addNewUser(User user);

    User getByUsername(String username);

    void updateUser(User user);

    void deleteUser(Long id);
}
