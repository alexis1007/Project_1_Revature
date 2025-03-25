package org.example.Service;


import org.example.model.MailingAddress;
import org.example.repository.MailingAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MailingAddressService implements MailingAddressInterface {

    private final MailingAddressRepository mailingAddressRepository;

    @Autowired
    public MailingAddressService(MailingAddressRepository mailingAddressRepository) {
        this.mailingAddressRepository = mailingAddressRepository;
    }

    // Getting all directions
    @Override
    public List<MailingAddress> findAllAddresses() {
        return mailingAddressRepository.findAll();
    }

    // Getting address by id
    @Override
    public Optional<MailingAddress> findAddressById(Long id) {
        return mailingAddressRepository.findById(id);
    }

    // Creating new Address
    @Override
    public MailingAddress createAddress(MailingAddress mailingAddress) {
        return mailingAddressRepository.save(mailingAddress);
    }

    // Updating Address
    @Override
    public Optional<MailingAddress> updateAddress(Long id, MailingAddress mailingAddress) {
        return mailingAddressRepository.findById(id).map(existingAddress -> {
            existingAddress.setStreet(mailingAddress.getStreet());
            existingAddress.setCity(mailingAddress.getCity());
            existingAddress.setState(mailingAddress.getState());
            existingAddress.setZip(mailingAddress.getZip());
            existingAddress.setCountry(mailingAddress.getCountry());
            return mailingAddressRepository.save(existingAddress);
        });
    }

    // Eliminar una dirección
    @Override
    public boolean deleteAddress(Long id) {
        return mailingAddressRepository.findById(id).map(address -> {
            mailingAddressRepository.delete(address);
            return true;
        }).orElse(false);
    }
}
