package org.example.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;


@Entity
    @Table(name = "users")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "user_id")

public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long userId;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "passwor_hash", nullable = false, length = 100)
    private String passwordHash;

    // Mark the "role" field as the back reference to avoid circular serialization.
//    @JsonBackReference(value = "roleUsers")

    @ManyToOne
    @JoinColumn(name = "user_types_id", nullable = false)
    private int userTypesId;

//    @OneToOne(cascade = CascadeType.ALL)
    private UserProfile userProfile;

    // getters and setters


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getUserTypesId() {
        return userTypesId;
    }

    public void setUserTypesId(int userTypesId) {
        this.userTypesId = userTypesId;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}

// some test change