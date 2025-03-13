package org.example.Service;

import java.util.List;
import java.util.Optional;

import org.example.model.UserProfile;
import org.example.repository.UserProfileRepository;

public class UserProfileService implements UserProfileServiceInterface {
    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public List<UserProfile> findAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    @Override
    public Optional<UserProfile> findUserProfileById(Long id) {
        return userProfileRepository.findById(id);
    }

    @Override
    public UserProfile registerUserProfile(UserProfile user) {
        return userProfileRepository.save(user);
    }

    @Override
    public Optional<UserProfile> updateUserProfile(Long id, UserProfile profile) {
        return userProfileRepository.findById(id).map(existingProfile -> {
            existingProfile.setFirstName(profile.getFirstName());
            existingProfile.setLastName(profile.getLastName());
            // Update additional fields as necessary
            return userProfileRepository.save(existingProfile);
        });
    }

    @Override
    public boolean deleteUserProfile(Long id) {
        return userProfileRepository.findById(id).map(profile -> {
            userProfileRepository.delete(profile);
            return true;
        }).orElse(false);
    }

}
