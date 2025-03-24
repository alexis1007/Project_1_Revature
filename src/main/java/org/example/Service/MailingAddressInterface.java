package org.example.Service;

import org.example.model.MailingAddress;

import java.util.List;
import java.util.Optional;
public interface MailingAddressInterface {

    // Obtener todas las direcciones
    List<MailingAddress> findAllAddresses();

    // Obtener una dirección por ID
    Optional<MailingAddress> findAddressById(Long id);

    // Crear una nueva dirección
    MailingAddress createAddress(MailingAddress mailingAddress);

    // Actualizar una dirección existente
    Optional<MailingAddress> updateAddress(Long id, MailingAddress mailingAddress);

    // Eliminar una dirección
    boolean deleteAddress(Long id);
}
