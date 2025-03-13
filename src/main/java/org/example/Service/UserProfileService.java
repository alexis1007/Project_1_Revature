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
        // TODO Auto-generated method stub
        return userProfileRepository.findAll();
    }

    @Override
    public Optional<UserProfile> findUserProfileById(Long id) {
        // TODO Auto-generated method stub
        return userProfileRepository.findById(id);
    }

    @Override
    public UserProfile registerUserProfile(UserProfile user) {
        // TODO Auto-generated method stub
        return userProfileRepository.save(user);
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
