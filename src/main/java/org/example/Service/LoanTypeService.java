package org.example.Service;

import org.example.model.LoanType;
import org.example.repository.LoanTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LoanTypeService implements LoanTypeInterface{

    private final LoanTypeRepository loanTypeRepository;
    @Autowired
    public LoanTypeService(LoanTypeRepository loanTypeRepository) {
        this.loanTypeRepository = loanTypeRepository;
    }


    @Override
    public List<LoanType> findAllLoanTypes() {
        return loanTypeRepository.findAll();
    }

    @Override
    public Optional<LoanType> findLoanTypeById(Long id) {
        return loanTypeRepository.findById(id);
    }

    @Override
    public LoanType createLoanType(LoanType loanType) {
        return loanTypeRepository.save(loanType);
    }

    @Override
    public Optional<LoanType> updateLoanType(Long id, LoanType loanType) {
        return loanTypeRepository.findById(id).map(existingLoanType -> {
            existingLoanType.setLoanType(loanType.getLoanType());
            return loanTypeRepository.save(existingLoanType);
        });
    }

    @Override
    public boolean deleteLoanType(Long id) {
        return loanTypeRepository.findById(id).map(loanType -> {
            loanTypeRepository.delete(loanType);
            return true;
        }).orElse(false);
    }
}
