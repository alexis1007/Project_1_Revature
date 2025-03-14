package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "loan_applications", schema = "loans")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_applications_id")
    private Long id;
    @Column(name="principal_balance", precision = 10, scale = 2)
    private Double principalBalance;
    @Column(name = "interest", precision = 5, scale = 2)
    private Double interest;
    @Column(name="term_length")
    private Integer termLength;
    @Column(name="total_balance", precision = 10, scale = 2)
    private Double totalBalance;

    @ManyToOne
    @JoinColumn(name = "application_statuses_id")
    private ApplicationStatus applicationStatus;
    @ManyToOne
    @JoinColumn(name = "loan_type_id")
    private LoanType loanType;
    @ManyToOne
    @JoinColumn(name = "user_profiles_id")
    private UserProfile userProfile;

    public LoanApplication() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Double getPrincipalBalance() {
        return principalBalance;
    }

    public void setPrincipalBalance(Double principalBalance) {
        this.principalBalance = principalBalance;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Integer getTermLength() {
        return termLength;
    }

    public void setTermLength(Integer termLength) {
        this.termLength = termLength;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    @Override
    public String toString() {
        return "LoanApplication{" +
                "id=" + id +
                ", applicationStatus=" + applicationStatus +
                ", loanType=" + loanType +
                ", userProfile=" + userProfile +
                ", principalBalance=" + principalBalance +
                ", interest=" + interest +
                ", termLength=" + termLength +
                ", totalBalance=" + totalBalance +
                '}';
    }
}

