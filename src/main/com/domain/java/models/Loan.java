package com.domain.java.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import com.domain.java.enums.LoanStatus;


@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;
    private BigDecimal loanAmount;
    private LoanStatus status;
    private Integer term;

    public Loan() {
    }

    public Loan(Long loanId, BigDecimal loanAmount, LoanStatus status, Integer term) {
        this.loanId = loanId;
        this.loanAmount = loanAmount;
        this.status = status;
        this.term = term;
    }


    public Long getId() {
        return loanId;
    }

    public void setId(Long loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }


    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = LoanStatus.valueOf(status.name());
    }

}
