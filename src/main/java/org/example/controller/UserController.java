package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.Service.UserService;
import org.example.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService=userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(HttpServletRequest request){
        // Only manager can gat a list of all users
        User sessionUser = (User) request.getAttribute("user");
        if(sessionUser.getUserType().getId() != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id, HttpServletRequest request) {
        // Only manager and user itself can get user information
        User sessionUser = (User) request.getAttribute("user");
        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody User userDetails,
                                           HttpServletRequest request){
        // Only manager and user itself can update user
        User sessionUser = (User) request.getAttribute("user");
        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return userService.updateUser(id, userDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        // Only manager and user itself can delete user
        User sessionUser = (User) request.getAttribute("user");
        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
