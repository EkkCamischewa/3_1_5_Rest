package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.utils.UserErrorResponsible;
import ru.kata.spring.boot_security.demo.utils.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.utils.UserNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/test")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    //    @ResponseBody
    @GetMapping("/{id}")
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody User user) {
        if (user == null || user.getUsername()==null || user.getUsername().isEmpty()
                || user.getPassword().isEmpty() || user.getPassword()==null) {
            throw new UserNotCreatedException("User has not been created");
        }
        userService.addNewUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> editUser(@PathVariable("id") Long id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponsible> handleException(UserNotFoundException e) {
        UserErrorResponsible userErrorResponsible = new UserErrorResponsible(
                "User not found",
                System.currentTimeMillis());
        return new ResponseEntity<>(userErrorResponsible, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(UserNotCreatedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
