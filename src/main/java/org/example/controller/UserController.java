package org.example.controller;

import java.util.List;

import org.example.Service.UserService;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ROLE_manager')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(HttpServletRequest request) {
        // Only manager can get a list of all users
        User sessionUser = (User) request.getAttribute("user");

        //log user
        log.info("User [{}] wants list of all users", sessionUser.getId());
        if(sessionUser.getUserType().getId() != 1) {
            log.warn("Access Denied for user [{}]", sessionUser.getId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        log.info("Users retrieve [{}]", userService.findAllUsers().size());
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PreAuthorize("hasAuthority('ROLE_manager') or hasAuthority('ROLE_regular')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id, HttpServletRequest request) {
        User sessionUser = (User) request.getAttribute("user");
        log.info("User [{}] requests user with id [{}]", sessionUser.getId(), id);

        // Regular users can only access their own data
        if(sessionUser.getUserType().getId() != 1 && !sessionUser.getId().equals(id)) {
            log.warn("Access Denied for user [{}]", sessionUser.getId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return userService.findUserById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_manager')")
    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        log.info("User registered with id [{}]", savedUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PreAuthorize("hasAuthority('ROLE_manager') or hasAuthority('ROLE_regular')")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody User userDetails,
                                           HttpServletRequest request) {
        User sessionUser = (User) request.getAttribute("user");
        log.info("User [{}] attempting to update user [{}]", sessionUser.getId(), id);

        // Regular users can only update their own data
        if(sessionUser.getUserType().getId() != 1 && !sessionUser.getId().equals(id)) {
            log.warn("Access Denied for user [{}]", sessionUser.getId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return userService.updateUser(id, userDetails)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_manager')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        log.info("Deleted user with id [{}]", id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
