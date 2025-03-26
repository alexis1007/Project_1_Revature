package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.MailingAddress;
import org.example.Service.MailingAddressService;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mailing")
public class MailingAddressController {

    private final MailingAddressService mailingAddressService;


    @Autowired
    public MailingAddressController(MailingAddressService mailingAddressService) {
        this.mailingAddressService = mailingAddressService;
    }

    // Obtener todas las direcciones
    @GetMapping
    public ResponseEntity<?> getAllMailingAddresses(HttpServletRequest request) {
        User sessionUser = (User) request.getAttribute("user");

        if(sessionUser.getUserType().getId() != 1 ) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<MailingAddress> addresses = mailingAddressService.findAllAddresses();
        return ResponseEntity.ok(addresses);
    }
   /*
   * Get a new direction by id
   */

    @GetMapping("/{id}")
    public ResponseEntity<MailingAddress> getMailingAddressById(@PathVariable Long id, HttpServletRequest request) {
        User sessionUser = (User) request.getAttribute("user");

        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<MailingAddress> address = mailingAddressService.findAddressById(id);

        return address.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Crear una nueva dirección
    @PostMapping
    public ResponseEntity<MailingAddress> createMailingAddress(@RequestBody MailingAddress mailingAddress) {


        MailingAddress createdAddress = mailingAddressService.createAddress(mailingAddress);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    // Actualizar una dirección existente
    @PutMapping("/{id}")
    public ResponseEntity<MailingAddress> updateMailingAddress(@PathVariable Long id, @RequestBody MailingAddress mailingAddress, HttpServletRequest request) {
        // Only manager and user itself can update user
        User sessionUser = (User) request.getAttribute("user");
        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }


        Optional<MailingAddress> updatedAddress = mailingAddressService.updateAddress(id, mailingAddress);
        return updatedAddress.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Eliminar una dirección
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMailingAddress(@PathVariable Long id, HttpServletRequest request) {
        User sessionUser = (User) request.getAttribute("user");


        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        boolean isDeleted = mailingAddressService.deleteAddress(id);
        return isDeleted ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}