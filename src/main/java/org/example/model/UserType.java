package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_type") // Changed from plural to singular
public class UserType { // Changed class name to singular
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_type_id") // Updated column name
    private int id;

    @Column(name = "user_type", length = 45)
    private String userType;
    
    // Default constructor
    public UserType() {}
    
    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
