package org.example.DTO;

import org.example.model.LoanApplication;

public class LoanResponseDto {
    private LoanApplication loanApplication;
    private String motivationalQuote = "";

    public LoanResponseDto(LoanApplication loanApplication, String motivationalQuote) {
        this.loanApplication = loanApplication;
        this.motivationalQuote = motivationalQuote;
    }

    public LoanApplication getLoan() {
        return loanApplication;
    }

    public void setLoan(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }

    public String getMotivationalQuote() {
        return motivationalQuote;
    }

    public void setMotivationalQuote(String motivationalQuote) {
        this.motivationalQuote = motivationalQuote;
    }

}
