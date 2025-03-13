package org.example.Service;

import java.util.List;
import java.util.Optional;

import org.example.model.UserProfile;
import org.example.repository.UserProfileRepository;

public class UserProfileService implements UserProfileServiceInterface {
    private final UserProfileRepository userRepository;

    public UserProfileService(UserProfileRepository userRepository) {
        // TODO Auto-generated method stub
        this.userRepository = userRepository;
    }

    @Override
    public List<UserProfile> findAllUserProfiles() {
        // TODO Auto-generated method stub
        return userRepository.findAll();
    }

    @Override
    public Optional<UserProfile> findUserProfileById(Long id) {
        // TODO Auto-generated method stub
        return userRepository.findById(id);
    }

    @Override
    public UserProfile registerUserProfile(UserProfile user) {
        // TODO Auto-generated method stub
        return userRepository.save(user);
    }

    @Override
    public Optional<UserProfile> updateUserProfile(Long id, UserProfile profile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUserProfile'");
    }

    @Override
    public boolean deleteUserProfile(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUserProfile'");
    }

}
