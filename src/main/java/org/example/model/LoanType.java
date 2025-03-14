package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "loan_type", schema = "loans")
public class LoanType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_type_id")
    private Long id;

    @Column(name = "loan_type", length = 10, unique = true)
    private String loanType;

    public LoanType() {
    }

    public Long getLoanTypeId() {
        return id;
    }

    public void setLoanTypeId(Long id) {
        this.id = id;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    @Override
    public String toString() {
        return "LoanType{" +
                "id=" + id +
                ", loanType='" + loanType + '\'' +
                '}';
    }
}
