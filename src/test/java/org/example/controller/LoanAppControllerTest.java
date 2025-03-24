package org.example.controller;

import org.example.Service.LoanApplicationService;
import org.example.Service.MotivationalQuoteService;
import org.example.model.LoanApplication;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanAppController.class)
public class LoanAppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanApplicationService loanApplicationService;

    @MockBean
    private MotivationalQuoteService motivationalQuoteService;

    private LoanApplication loanApplication;

    @BeforeEach
    public void setUp() {
        loanApplication = new LoanApplication();
        loanApplication.setId(1L);
        loanApplication.setPrincipalBalance(new BigDecimal("1000.00"));
        loanApplication.setInterest(new BigDecimal("5.00"));
        loanApplication.setTermLength(12);
        loanApplication.setTotalBalance(new BigDecimal("1050.00"));
    }

    @Test
    public void testGetAllLoans() throws Exception {
        when(loanApplicationService.findAllLoans()).thenReturn(Collections.singletonList(loanApplication));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/loans")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id': 1, 'principalBalance': 1000.00, 'interest': 5.00, 'termLength': 12, 'totalBalance': 1050.00}]"));
    }

    @Test
    public void testGetLoanById() throws Exception {
        when(loanApplicationService.findLoanById(1L)).thenReturn(Optional.of(loanApplication));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/loans/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 1, 'principalBalance': 1000.00, 'interest': 5.00, 'termLength': 12, 'totalBalance': 1050.00}"));
    }

    @Test
    public void testCreateLoan() throws Exception {
        when(loanApplicationService.createLoan(any(LoanApplication.class))).thenReturn(loanApplication);
        when(motivationalQuoteService.getRandomQuote()).thenReturn("Keep going!");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{'principalBalance': 1000.00, 'interest': 5.00, 'termLength': 12, 'totalBalance': 1050.00}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{'loanApplication': {'id': 1, 'principalBalance': 1000.00, 'interest': 5.00, 'termLength': 12, 'totalBalance': 1050.00}, 'quote': 'Keep going!'}"));
    }

    @Test
    public void testUpdateLoan() throws Exception {
        when(loanApplicationService.updateLoan(any(Long.class), any(LoanApplication.class))).thenReturn(Optional.of(loanApplication));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/loans/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{'principalBalance': 1000.00, 'interest': 5.00, 'termLength': 12, 'totalBalance': 1050.00}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 1, 'principalBalance': 1000.00, 'interest': 5.00, 'termLength': 12, 'totalBalance': 1050.00}"));
    }

    @Test
    public void testDeleteLoan() throws Exception {
        when(loanApplicationService.deleteLoan(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/loans/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testApproveLoan() throws Exception {
        when(loanApplicationService.approveLoan(any(Long.class), any(LoanApplication.class))).thenReturn(Optional.of(loanApplication));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/loans/1/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{'applicationStatus': {'id': 2, 'status': 'APPROVED'}}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 1, 'principalBalance': 1000.00, 'interest': 5.00, 'termLength': 12, 'totalBalance': 1050.00}"));
    }

    @Test
    public void testRejectLoan() throws Exception {
        when(loanApplicationService.rejectLoan(any(Long.class), any(LoanApplication.class))).thenReturn(Optional.of(loanApplication));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/loans/1/reject")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{'applicationStatus': {'id': 3, 'status': 'REJECTED'}}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 1, 'principalBalance': 1000.00, 'interest': 5.00, 'termLength': 12, 'totalBalance': 1050.00}"));
    }
}
