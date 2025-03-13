package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_types")
public class UserTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_types_id")
    private int id;

    @Column(name = "user_type", length = 45, unique = true)
    private String userType;
    
    public int getId(){
        return id;
    }
    public String getUserType(){
        return userType;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setUserType(String userType){
        this.userType = userType;
    }
}
