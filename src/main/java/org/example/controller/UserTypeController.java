package org.example.controller;

import java.util.List;

import org.example.Service.UserTypeService;
import org.example.model.User;
import org.example.model.UserType;
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
@RequestMapping("/api/user_types")  // Mantenemos el guion bajo para consistencia
public class UserTypeController {
    private static final Logger log = LoggerFactory.getLogger(UserTypeController.class);
    private final UserTypeService userTypeService;

    public UserTypeController(UserTypeService userTypeService){
        this.userTypeService = userTypeService;
    }

    // Endpoint público para pruebas
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        log.info("Accediendo al endpoint de prueba");
        return ResponseEntity.ok("UserTypeController está funcionando correctamente");
    }

    // Ver tipos de usuario - accesible para managers y usuarios regulares
    @PreAuthorize("hasAuthority('ROLE_manager') or hasAuthority('ROLE_regular')")
    @GetMapping
    public ResponseEntity<List<UserType>> getAllUserTypes(HttpServletRequest request) {
        User sessionUser = (User) request.getAttribute("user");
        log.info("Usuario [{}] solicitando todos los tipos de usuario", 
                 sessionUser != null ? sessionUser.getId() : "desconocido");
        
        List<UserType> types = userTypeService.findAllUserTypes();
        log.info("Recuperados {} tipos de usuario", types.size());
        return ResponseEntity.ok(types);
    }

    @PreAuthorize("hasAuthority('ROLE_manager') or hasAuthority('ROLE_regular')")
    @GetMapping("/{id}")
    public ResponseEntity<UserType> getUserTypeById(@PathVariable Long id, HttpServletRequest request) {
        User sessionUser = (User) request.getAttribute("user");
        log.info("Usuario [{}] solicitando tipo de usuario con ID [{}]", 
                 sessionUser != null ? sessionUser.getId() : "desconocido", id);
        
        try {
            return userTypeService.findUserTypeById(id)
                    .map(userType -> {
                        log.info("Tipo de usuario encontrado: {}", userType.getUserType());
                        return ResponseEntity.ok(userType);
                    })
                    .orElseGet(() -> {
                        log.warn("Tipo de usuario con ID [{}] no encontrado", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error al recuperar tipo de usuario con ID [{}]: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Operaciones administrativas - solo para managers
    @PreAuthorize("hasAuthority('ROLE_manager')")
    @PostMapping()
    public ResponseEntity<UserType> createUserType(@RequestBody UserType userType) {
         UserType savedUserType = userTypeService.createUserType(userType);
         return ResponseEntity.status(HttpStatus.CREATED).body(savedUserType);
    }

    @PreAuthorize("hasAuthority('ROLE_manager')")
    @PutMapping("/{id}")
    public ResponseEntity<UserType> updateUserType
            (@PathVariable Long id, @RequestBody UserType userTypeDetails){
            return userTypeService.updateUserType(id, userTypeDetails)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_manager')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserType(@PathVariable Long id) {
        boolean deleted = userTypeService.deleteUserType(id);
        return deleted ? ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
