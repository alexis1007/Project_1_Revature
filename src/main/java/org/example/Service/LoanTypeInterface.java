package org.example.Service;

import org.example.model.LoanType;

import java.util.List;
import java.util.Optional;

public interface LoanTypeInterface {
        //Getting all loans type
        List<LoanType> findAllLoanTypes();
        //Find a loantype by id
        Optional<LoanType> findLoanTypeById(Long id);
        //Making a new loantype
        LoanType createLoanType(LoanType loanType);
        //Updating a loanType
        Optional<LoanType> updateLoanType(Long id, LoanType loanType);
        //Deleting a loan type
        boolean deleteLoanType(Long id);
}
