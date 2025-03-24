package org.example.controller;

import org.example.DTO.AuthRequest;
import org.example.DTO.AuthResponse;
import org.example.Service.JwtService;
import org.example.Service.UserProfileService;
import org.example.Service.UserService;
import org.example.model.User;
import org.example.model.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final UserProfileService userProfileService;

    public AuthController(UserService userService, UserProfileService userProfileService, JwtService jwtService) {
        this.userService = userService;
        this.userProfileService = userProfileService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserProfile> registerUser(@RequestBody UserProfile userProfile) {

        User savedUser = userService.registerUser(userProfile.getUser());
        userProfile.setUser(savedUser);
        UserProfile savedUserProfile = userProfileService.registerUserProfile(userProfile);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserProfile);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Optional<User> userOpt = userService.validateUser(authRequest.getUsername(), authRequest.getPassword());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = jwtService.generateToken(
                user.getUsername(), 
                user.getUserType().getUserType()
            );
            return ResponseEntity.ok(new AuthResponse(token, user.getUserType().getUserType()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }

    // Con JWT no necesitamos un endpoint de logout explícito,
    // ya que el token se maneja completamente en el cliente
}
