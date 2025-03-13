package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_profiles_id")
    private Long user_profile_id;

    @OneToOne
    @JoinColumn(name = "users_id", nullable = false)
    private Long user_id; // replace to private User user;

    @Column(name = "mailing_address", nullable = false)
    private Long mailingAddressesId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String username;

    @Column(name = "last_name", nullable = false, length = 50)
    private String passwordHash;

    @Column(name = "user_types_id", nullable = false)
    private Long userTypesId;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "credit_score")
    private Long creditScore;

}
