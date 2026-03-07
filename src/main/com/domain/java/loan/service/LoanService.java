package com.domain.java.loan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.domain.java.exceptions.ResourceNotFoundException;
import com.domain.java.models.Loan;
import com.domain.java.repository.LoanRepository;


@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id));
    }
    
}
