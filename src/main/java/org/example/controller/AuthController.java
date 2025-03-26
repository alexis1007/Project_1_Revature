package org.example.controller;

import java.util.Optional;

import org.example.DTO.AuthRequest;
import org.example.DTO.AuthResponse;
import org.example.Service.JwtService;
import org.example.Service.UserProfileService;
import org.example.Service.UserService;
import org.example.Service.UserTypeService;
import org.example.model.User;
import org.example.model.UserProfile;
import org.example.model.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final JwtService jwtService;
    private final UserProfileService userProfileService;
    private final UserTypeService userTypeService;

    public AuthController(UserService userService, UserProfileService userProfileService, JwtService jwtService, UserTypeService userTypeService) {
        this.userService = userService;
        this.userProfileService = userProfileService;
        this.jwtService = jwtService;
        this.userTypeService = userTypeService;
    }

    // Este endpoint debe permanecer público para permitir registro de nuevos usuarios
    @PostMapping("/register")
    public ResponseEntity<UserProfile> registerUser(@RequestBody UserProfile userProfile) {
        log.info("Register new user");
        
        // Asignar rol de usuario regular por defecto (ID 2)
        if (userProfile.getUser().getUserType() == null) {
            Optional<UserType> regularUserType = userTypeService.findUserTypeById(2L);
            if (regularUserType.isPresent()) {
                userProfile.getUser().setUserType(regularUserType.get());
            }
        }
        
        String plainPassword = userProfile.getUser().getPasswordHash();
        log.debug("Registering user with password (length): {}", 
                  plainPassword != null ? plainPassword.length() : "null");
        
        User savedUser = userService.registerUser(userProfile.getUser());
        userProfile.setUser(savedUser);
        UserProfile savedUserProfile = userProfileService.registerUserProfile(userProfile);
        log.info("Success register user: {}", savedUser.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserProfile);
    }

    // Este endpoint debe permanecer público para permitir login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Optional<User> userOpt = userService.validateUser(authRequest.getUsername(), authRequest.getPassword());
        log.info("Login attempt for user: {}", authRequest.getUsername());
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String userType = (user.getUserType() != null) ? user.getUserType().getUserType() : "DEFAULT";
            String token = jwtService.generateToken(user.getUsername(), userType);
            log.info("Login successful for user: {}", user.getUsername());
            return ResponseEntity.ok(new AuthResponse(token, user));
        }
        
        log.warn("Login failed for user: {}", authRequest.getUsername());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    // Ejemplo de endpoint protegido (si deseas agregar uno)
    @PreAuthorize("hasAuthority('ROLE_manager')")
    @PostMapping("/admin")
    public ResponseEntity<?> adminOperation() {
        // Lógica solo para administradores
        return ResponseEntity.ok("Admin operation successful");
    }
}
