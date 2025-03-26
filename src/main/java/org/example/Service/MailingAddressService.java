package org.example.Service;


import org.example.model.MailingAddress;
import org.example.model.UserProfile;
import org.example.repository.MailingAddressRepository;
import org.example.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MailingAddressService implements MailingAddressInterface {

    private final MailingAddressRepository mailingAddressRepository;
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public MailingAddressService(MailingAddressRepository mailingAddressRepository, UserProfileRepository userProfileRepository) {
        this.mailingAddressRepository = mailingAddressRepository;
        this.userProfileRepository = userProfileRepository;
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
        Optional<MailingAddress> currentAddress = mailingAddressRepository.findById(id);
        UserProfile currentUserProfile = currentAddress.get().getUserProfile();
        currentUserProfile.setMailingAddress(null);
        userProfileRepository.save(currentUserProfile);
        return mailingAddressRepository.findById(id).map(address -> {
            mailingAddressRepository.delete(address);
            return true;
        }).orElse(false);
    }
}
