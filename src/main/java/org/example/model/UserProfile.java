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
    private Long usersProfilesId;

    @OneToOne
    @JoinColumn(name = "users_id", nullable = false)
    private Long usersId; // replace to private User user;

    @Column(name = "mailing_address", nullable = false)
    private Long mailingAddressesId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "user_types_id", nullable = false)
    private Long userTypesId;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "credit_score")
    private Long creditScore;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
