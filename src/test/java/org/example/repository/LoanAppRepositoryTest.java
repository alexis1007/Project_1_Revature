package org.example.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.example.model.ApplicationStatus;
import org.example.model.LoanApplication;
import org.example.model.LoanType;
import org.example.model.User;
import org.example.model.UserProfile;
import org.example.model.UserType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LoanAppRepositoryTest {

    @Autowired
    private LoanAppRepository loanAppRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;


    // Bertin
    /*
    @Autowired
    private ApplicationStatusRepository applicationStatusRepository;

    @Autowired
    private LoanTypeRepository loanTypeRepository;
    */

    private UserType userType;
    private User user;
    private UserProfile userProfile;
    private ApplicationStatus applicationStatus;
    private LoanType loanType;

    @BeforeEach
    public void setUp() {
        // Clean any existing data
        loanAppRepository.deleteAll();
        userProfileRepository.deleteAll();
        userRepository.deleteAll();
        userTypeRepository.deleteAll();
        /*
        applicationStatusRepository.deleteAll();
        loanTypeRepository.deleteAll();
        */
        
        // Set up required entities
        userType = new UserType();
        userType.setUserType("USER");
        userTypeRepository.save(userType);

        user = new User();
        user.setUsername("testuser" + System.currentTimeMillis()); // Ensure unique username
        user.setPasswordHash("password");
        user.setUserType(userType);
        userRepository.save(user);

        userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
        userProfile.setPhoneNumber("123-456-7890");
        userProfile.setCreditScore(700);
        userProfile.setBirthDate(LocalDate.of(1990, 1, 1));
        userProfileRepository.save(userProfile);

        applicationStatus = new ApplicationStatus();
        applicationStatus.setStatus("PENDING");
        //applicationStatusRepository.save(applicationStatus);

        loanType = new LoanType();
        /*
        loanType.setType("PERSONAL");
        loanTypeRepository.save(loanType);
        */
    }

    @Test
    public void testSaveLoanApplication() {
        // Create loan application
        LoanApplication loanApplication = createSampleLoanApplication();
        
        // Verify it was saved correctly
        Optional<LoanApplication> foundLoanApplication = loanAppRepository.findById(loanApplication.getId());
        assertTrue(foundLoanApplication.isPresent(), "Loan application should be found after saving");
        assertEquals(new BigDecimal("10000.00").setScale(2), 
                     foundLoanApplication.get().getPrincipalBalance().setScale(2),
                     "Principal balance should match");
    }

    @Test
    public void testUpdateLoanApplication() {
        // Create loan application
        LoanApplication loanApplication = createSampleLoanApplication();
        
        // Update it
        loanApplication.setPrincipalBalance(new BigDecimal("15000.00"));
        loanAppRepository.save(loanApplication);
        
        // Verify update
        Optional<LoanApplication> foundLoanApplication = loanAppRepository.findById(loanApplication.getId());
        assertTrue(foundLoanApplication.isPresent(), "Updated loan application should be found");
        assertEquals(new BigDecimal("15000.00").setScale(2), 
                     foundLoanApplication.get().getPrincipalBalance().setScale(2),
                     "Updated principal balance should match");
    }

    @Test
    public void testDeleteLoanApplication() {
        // Create loan application
        LoanApplication loanApplication = createSampleLoanApplication();
        
        // Delete it
        loanAppRepository.delete(loanApplication);
        
        // Verify deletion
        Optional<LoanApplication> foundLoanApplication = loanAppRepository.findById(loanApplication.getId());
        assertFalse(foundLoanApplication.isPresent(), "Loan application should not be found after deletion");
    }
    
    private LoanApplication createSampleLoanApplication() {
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setUserProfile(userProfile);
        loanApplication.setApplicationStatus(applicationStatus);
        loanApplication.setLoanType(loanType);
        loanApplication.setPrincipalBalance(new BigDecimal("10000.00"));
        loanApplication.setInterest(new BigDecimal("5.00"));
        loanApplication.setTermLength(12);
        loanApplication.setTotalBalance(new BigDecimal("10500.00"));
        return loanAppRepository.save(loanApplication);
    }
}
