package org.example.Service;

import org.example.model.User;
import org.example.model.UserType;
import org.example.repository.UserRepository;
import org.example.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface{
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;

    public UserService(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        this.userRepository=userRepository;
        this.userTypeRepository=userTypeRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public User registerUser(User user){
        Long userTypeId = user.getUserType().getId();
        UserType userType = userTypeRepository.findById(userTypeId)
                .orElseThrow(() -> new RuntimeException("User type not found with id " + userTypeId));
        userRepository.findByUsername(user.getUsername()).ifPresent(existingUser -> {
            throw new RuntimeException("Username " + existingUser.getUsername() + " already exists");
        });
        return userRepository.save(user);
    }

    @Override
    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(existingUserById -> {
            Optional.ofNullable(userDetails.getUsername())
                    .ifPresent(newUsername -> {
                        userRepository.findByUsername(newUsername).ifPresent(existingUsername -> {
                            if(!existingUsername.getUsername().equals(existingUserById.getUsername())) {
                                throw new RuntimeException("Username " + existingUsername.getUsername() + " already exists");
                            }
                        });
                        existingUserById.setUsername(newUsername);
                    });
            Optional.ofNullable(userDetails.getPasswordHash())
                    .ifPresent(existingUserById::setPasswordHash);
            Optional.ofNullable(userDetails.getUserType())
                    .ifPresent(newUserType -> {
                        existingUserById.setUserType(userTypeRepository.findById(newUserType.getId())
                                .orElseThrow(() -> new RuntimeException("User type not found with id " + newUserType.getId())));
                    });

            return userRepository.save(existingUserById);
        });
    }

    @Override
    public boolean deleteUser(Long id){
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }
}