package org.example.controller;

import org.example.Service.LoanTypeService;
import org.example.model.LoanType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/type")
public class LoanTypeController {
    private final LoanTypeService loanTypeService;

    @Autowired
    public LoanTypeController(LoanTypeService loanTypeService) {
        this.loanTypeService = loanTypeService;
    }

    //Getting type by id

    @GetMapping
    public List<LoanType> getAllLoanTypes(){return loanTypeService.findAllLoanTypes();}

    @GetMapping("/{id}")
    public ResponseEntity<LoanType> getLoanTypeById(@PathVariable Long id) {
        Optional<LoanType> loanType = loanTypeService.findLoanTypeById(id);
        return loanType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<LoanType> createLoanType(@RequestBody LoanType loanType){
        LoanType createLoanType = loanTypeService.createLoanType(loanType);
        return ResponseEntity.status(HttpStatus.CREATED).body(createLoanType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanType> updateLoanType(@PathVariable Long id, @RequestBody LoanType loanType){
        Optional<LoanType> updateLoanType = loanTypeService.updateLoanType(id, loanType);
        return updateLoanType.map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteLoanType(@PathVariable Long id){
        boolean isDeleted = loanTypeService.deleteLoanType(id);
        return isDeleted ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
