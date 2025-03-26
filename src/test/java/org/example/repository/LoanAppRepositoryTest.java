package org.example.repository;

<<<<<<< HEAD
import org.example.model.LoanApplication;
import org.example.model.ApplicationStatus;
import org.example.model.LoanType;
import org.example.model.UserProfile;
import org.example.model.User;
import org.example.model.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

=======
>>>>>>> 63795e407baa4c0aaa78ae2b3ef052ed9555c681
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

<<<<<<< HEAD
import static org.junit.jupiter.api.Assertions.*;
=======
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
>>>>>>> 63795e407baa4c0aaa78ae2b3ef052ed9555c681

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

<<<<<<< HEAD
=======

    // Bertin
    /*
>>>>>>> 63795e407baa4c0aaa78ae2b3ef052ed9555c681
    @Autowired
    private ApplicationStatusRepository applicationStatusRepository;

    @Autowired
    private LoanTypeRepository loanTypeRepository;
<<<<<<< HEAD

    @Test
    public void testSaveLoanApplication() {
        UserType userType = new UserType();
        userType.setUserType("USER");
        userTypeRepository.save(userType);

        User user = new User();
        user.setUsername("testuser");
=======
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
>>>>>>> 63795e407baa4c0aaa78ae2b3ef052ed9555c681
        user.setPasswordHash("password");
        user.setUserType(userType);
        userRepository.save(user);

<<<<<<< HEAD
        UserProfile userProfile = new UserProfile();
=======
        userProfile = new UserProfile();
>>>>>>> 63795e407baa4c0aaa78ae2b3ef052ed9555c681
        userProfile.setUser(user);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
        userProfile.setPhoneNumber("123-456-7890");
        userProfile.setCreditScore(700);
        userProfile.setBirthDate(LocalDate.of(1990, 1, 1));
        userProfileRepository.save(userProfile);

<<<<<<< HEAD
        ApplicationStatus applicationStatus = new ApplicationStatus();
        applicationStatus.setStatus("PENDING");
        applicationStatusRepository.save(applicationStatus);

        LoanType loanType = new LoanType();
        loanType.setType("PERSONAL");
        loanTypeRepository.save(loanType);

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setUserProfile(userProfile);
        loanApplication.setApplicationStatus(applicationStatus);
        loanApplication.setLoanType(loanType);
        loanApplication.setPrincipalBalance(new BigDecimal("10000.00"));
        loanApplication.setInterest(new BigDecimal("5.00"));
        loanApplication.setTermLength(12);
        loanApplication.setTotalBalance(new BigDecimal("10500.00"));
        loanAppRepository.save(loanApplication);

        Optional<LoanApplication> foundLoanApplication = loanAppRepository.findById(loanApplication.getId());
        assertTrue(foundLoanApplication.isPresent());
        assertEquals(new BigDecimal("10000.00"), foundLoanApplication.get().getPrincipalBalance());
=======
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
>>>>>>> 63795e407baa4c0aaa78ae2b3ef052ed9555c681
    }

    @Test
    public void testUpdateLoanApplication() {
<<<<<<< HEAD
        UserType userType = new UserType();
        userType.setUserType("USER");
        userTypeRepository.save(userType);

        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setUserType(userType);
        userRepository.save(user);

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
        userProfile.setPhoneNumber("123-456-7890");
        userProfile.setCreditScore(700);
        userProfile.setBirthDate(LocalDate.of(1990, 1, 1));
        userProfileRepository.save(userProfile);

        ApplicationStatus applicationStatus = new ApplicationStatus();
        applicationStatus.setStatus("PENDING");
        applicationStatusRepository.save(applicationStatus);

        LoanType loanType = new LoanType();
        loanType.setType("PERSONAL");
        loanTypeRepository.save(loanType);

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setUserProfile(userProfile);
        loanApplication.setApplicationStatus(applicationStatus);
        loanApplication.setLoanType(loanType);
        loanApplication.setPrincipalBalance(new BigDecimal("10000.00"));
        loanApplication.setInterest(new BigDecimal("5.00"));
        loanApplication.setTermLength(12);
        loanApplication.setTotalBalance(new BigDecimal("10500.00"));
        loanAppRepository.save(loanApplication);

        loanApplication.setPrincipalBalance(new BigDecimal("15000.00"));
        loanAppRepository.save(loanApplication);

        Optional<LoanApplication> foundLoanApplication = loanAppRepository.findById(loanApplication.getId());
        assertTrue(foundLoanApplication.isPresent());
        assertEquals(new BigDecimal("15000.00"), foundLoanApplication.get().getPrincipalBalance());
=======
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
>>>>>>> 63795e407baa4c0aaa78ae2b3ef052ed9555c681
    }

    @Test
    public void testDeleteLoanApplication() {
<<<<<<< HEAD
        UserType userType = new UserType();
        userType.setUserType("USER");
        userTypeRepository.save(userType);

        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setUserType(userType);
        userRepository.save(user);

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
        userProfile.setPhoneNumber("123-456-7890");
        userProfile.setCreditScore(700);
        userProfile.setBirthDate(LocalDate.of(1990, 1, 1));
        userProfileRepository.save(userProfile);

        ApplicationStatus applicationStatus = new ApplicationStatus();
        applicationStatus.setStatus("PENDING");
        applicationStatusRepository.save(applicationStatus);

        LoanType loanType = new LoanType();
        loanType.setType("PERSONAL");
        loanTypeRepository.save(loanType);

=======
        // Create loan application
        LoanApplication loanApplication = createSampleLoanApplication();
        
        // Delete it
        loanAppRepository.delete(loanApplication);
        
        // Verify deletion
        Optional<LoanApplication> foundLoanApplication = loanAppRepository.findById(loanApplication.getId());
        assertFalse(foundLoanApplication.isPresent(), "Loan application should not be found after deletion");
    }
    
    private LoanApplication createSampleLoanApplication() {
>>>>>>> 63795e407baa4c0aaa78ae2b3ef052ed9555c681
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setUserProfile(userProfile);
        loanApplication.setApplicationStatus(applicationStatus);
        loanApplication.setLoanType(loanType);
        loanApplication.setPrincipalBalance(new BigDecimal("10000.00"));
        loanApplication.setInterest(new BigDecimal("5.00"));
        loanApplication.setTermLength(12);
        loanApplication.setTotalBalance(new BigDecimal("10500.00"));
<<<<<<< HEAD
        loanAppRepository.save(loanApplication);

        loanAppRepository.delete(loanApplication);

        Optional<LoanApplication> foundLoanApplication = loanAppRepository.findById(loanApplication.getId());
        assertFalse(foundLoanApplication.isPresent());
=======
        return loanAppRepository.save(loanApplication);
>>>>>>> 63795e407baa4c0aaa78ae2b3ef052ed9555c681
    }
}
