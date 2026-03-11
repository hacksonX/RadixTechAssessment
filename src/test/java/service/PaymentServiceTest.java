package com.domain.java.payment.service;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean; 
import org.junit.jupiter.api.Test;

import com.domain.java.payment.service.PaymentService;
import com.domain.java.models.Loan;
import com.domain.java.models.Payment;
import com.domain.java.repository.LoanRepository;
import com.domain.java.repository.PaymentRepository;
import com.domain.java.enums.LoanStatus;

@SpringBootTest
public class PaymentServiceTest {
    
    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private LoanRepository loanRepository;

    @Autowired
    private PaymentService paymentService;

    @Test
    void processPayment_ShouldUpdateLoanBalanceAndStatus() {
        Long loanId = 1L;
        BigDecimal initialBalance = new BigDecimal("1000.00");
        BigDecimal paymentAmount = new BigDecimal("1000.00");

        Loan loan = new Loan();
        loan.setLoanId(loanId);
        loan.setLoanAmount(initialBalance);
        loan.setStatus(LoanStatus.ACTIVE);

        Payment payment = new Payment();
        payment.setLoanId(loanId);
        payment.setPaymentAmount(paymentAmount);

        when(loanRepository.findById(loanId)).thenReturn(java.util.Optional.of(loan));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Payment result = paymentService.processPayment(payment);

        org.junit.jupiter.api.Assertions.assertNotNull(result);
        org.junit.jupiter.api.Assertions.assertEquals(LoanStatus.SETTLED, loan.getStatus());
        org.junit.jupiter.api.Assertions.assertTrue(loan.getLoanAmount().compareTo(BigDecimal.ZERO) == 0);
    }

    @Test
    void processPayment_ShouldThrowException_WhenAmountExceedsBalance() {
        Long loanId = 1L;
        Loan loan = new Loan();
        loan.setLoanId(loanId);
        loan.setLoanAmount(new BigDecimal("500.00"));

        Payment payment = new Payment();
        payment.setLoanId(loanId);
        payment.setPaymentAmount(new BigDecimal("600.00"));

        when(loanRepository.findById(loanId)).thenReturn(java.util.Optional.of(loan));

        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () -> {
            paymentService.processPayment(payment);
        });
    }

}
