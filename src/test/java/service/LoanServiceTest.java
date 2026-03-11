package com.domain.java.loan.service;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

import com.domain.java.enums.LoanStatus;
import com.domain.java.loan.service.LoanService;
import com.domain.java.models.Loan;
import com.domain.java.repository.LoanRepository;

@SpringBootTest
public class LoanServiceTest {

    @MockBean
    private LoanRepository loanRepository;

    @Autowired
    private LoanService loanService;

    @Test
    void testFindLoanById() {
        Long loanId = 1L;
        Loan loan = new Loan();
        loan.setLoanId(loanId);
        loan.setLoanAmount(new BigDecimal("1000.00"));
        loan.setStatus(LoanStatus.ACTIVE);


        when(loanRepository.findById(loanId)).thenReturn(java.util.Optional.of(loan));

        Loan result = loanService.getLoanById(loanId);

        org.junit.jupiter.api.Assertions.assertNotNull(result);
        org.junit.jupiter.api.Assertions.assertEquals(loanId, result.getLoanId());
    }

    @Test
    void testCreateLoan() {
        Loan loan = new Loan();
        loan.setLoanAmount(new BigDecimal("1000.00"));
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setTerm(12);
        loan.setLoanId(1L);


        when(loanRepository.save(loan)).thenReturn(loan);

        Loan result = loanService.createLoan(loan);

        org.junit.jupiter.api.Assertions.assertNotNull(result);
        org.junit.jupiter.api.Assertions.assertNotNull(result.getLoanId());
    }
}
