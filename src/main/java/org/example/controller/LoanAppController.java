package org.example.controller;

import java.util.List;

import org.example.DTO.LoanResponseDto;
import org.example.Service.LoanApplicationService;
import org.example.Service.MotivationalQuoteService;
import org.example.model.LoanApplication;
import org.example.model.User;
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

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/loans")
public class LoanAppController {
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
        return ResponseEntity.ok(loanAppService.findAllLoans());
    }

    /**
     * Returns a specific loan by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LoanApplication> getLoanById(@PathVariable Long id) {
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
     * @param session The HttpSession containing the logged-in user.
     * @return A TaskResponseDto wrapping the saved task and a motivational quote.
     */
    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody LoanApplication loan, HttpSession session) {
        // Retrieve the logged-in user from the session.
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            // If no user is logged in, return 401 Unauthorized.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
        System.out.println(sessionUser.getUsername());
        // Associate the new loan with the logged-in user's profile.
        loan.setUserProfile(sessionUser.getUserProfile());
        // Save the loan via the service layer.
        LoanApplication savedLoan = loanAppService.createLoan(loan);
        // Retrieve a motivational quote from the external API.
        String quote = motivationalQuoteService.getRandomQuote();
        // Wrap the saved loan and quote in a response DTO.
        LoanResponseDto responseDto = new LoanResponseDto(savedLoan, quote);
        // Return the DTO with HTTP 201 Created.
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /**
     * Updates an existing task.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LoanApplication> updateLoan(@PathVariable Long id,
            @RequestBody LoanApplication loanApplication) {
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
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<LoanApplication> approveLoan(@PathVariable Long id,
            @RequestBody LoanApplication loanApplication) {
        return loanAppService.approveLoan(id, loanApplication)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LoanApplication> rejectLoan(@PathVariable Long id,
            @RequestBody LoanApplication loanApplication) {
        return loanAppService.rejectLoan(id, loanApplication)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
