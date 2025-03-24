package org.example.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.example.DTO.LoanResponseDto;
import org.example.Service.LoanApplicationService;
import org.example.Service.MotivationalQuoteService;
import org.example.model.LoanApplication;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@Slf4j
@RestController
@RequestMapping("/api/loans")
public class LoanAppController {
    private static final Logger log = LoggerFactory.getLogger(LoanAppController.class);
    private final LoanApplicationService loanAppService;
    private final MotivationalQuoteService motivationalQuoteService;

    public LoanAppController(LoanApplicationService loanAppService, MotivationalQuoteService motivationalQuoteService) {
        this.loanAppService = loanAppService;
        this.motivationalQuoteService = motivationalQuoteService;
    }

    /**
     * Returns all loans.
     */
    @GetMapping
    public ResponseEntity<List<LoanApplication>> getAllLoans() {
        log.info("Request all loans");
        return ResponseEntity.ok(loanAppService.findAllLoans());
    }

    /**
     * Returns a specific loan by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LoanApplication> getLoanById(@PathVariable Long id) {
        log.info("Request loan with id [{}]",id);
        return loanAppService.findLoanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new task for the currently logged-in user and returns the saved
     * task
     * along with a motivational quote.
     *
     * @param loan    The loan to be created.
     * @param request The HttpServletRequest containing the logged-in user.
     * @return A TaskResponseDto wrapping the saved task and a motivational quote.
     */
    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody LoanApplication loan, HttpServletRequest request) {
        User sessionUser = (User) request.getAttribute("user");
        log.info("User [{}] requests to create a new loan", sessionUser.getId());
        if (sessionUser == null) {
            log.warn("Access denied to user [{}]",sessionUser.getId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        loan.setUserProfile(sessionUser.getUserProfile());
        LoanApplication savedLoan = loanAppService.createLoan(loan);
        log.info("Loan created by user [{}]",sessionUser.getId());
        String quote = motivationalQuoteService.getRandomQuote();
        LoanResponseDto responseDto = new LoanResponseDto(savedLoan, quote);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /**
     * Updates an existing task.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LoanApplication> updateLoan(@PathVariable Long id,
            @RequestBody LoanApplication loanApplication) {
        log.info("Updating loan with id [{}]",id);
        return loanAppService.updateLoan(id, loanApplication)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a task.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        boolean deleted = loanAppService.deleteLoan(id);
        log.info("Deleteing loan with id [{}]",id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<LoanApplication> approveLoan(@PathVariable Long id,
            @RequestBody LoanApplication loanApplication) {
        log.info("Approving loan with id [{}]",id);
        return loanAppService.approveLoan(id, loanApplication)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LoanApplication> rejectLoan(@PathVariable Long id,
            @RequestBody LoanApplication loanApplication) {
        log.info("Rejecting loan with id [{}]",id);
        return loanAppService.rejectLoan(id, loanApplication)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
