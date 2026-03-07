package com.domain.java.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

import com.domain.java.exceptions.ResourceNotFoundException;
import com.domain.java.model.Payment;
import com.domain.java.repository.LoanRepository;
import com.domain.java.repository.PaymentRepository;
import com.domain.java.enums.LoanStatus;
import com.domain.java.model.Loan;

@Service
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final LoanRepository loanRepository;


    public PaymentService(PaymentRepository paymentRepository, LoanRepository loanRepository) {
        this.paymentRepository = paymentRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional
    public Payment processPayment(Payment payment) {
        if (payment.getPaymentAmount() == null || payment.getPaymentAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }

        Loan loan = loanRepository.findById(payment.getLoanId())
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + payment.getLoanId()));

        if (LoanStatus.SETTLED.equals(loan.getStatus())) {
            throw new IllegalStateException("Loan is already settled");
        }

        if (payment.getPaymentAmount().compareTo(loan.getLoanAmount()) > 0) {
            throw new IllegalStateException("Payment amount of " + payment.getPaymentAmount()
                    + " exceeds the remaining loan balance of " + loan.getLoanAmount());
        }

        BigDecimal newLoanBalance = loan.getLoanAmount().subtract(payment.getPaymentAmount());
        loan.setLoanAmount(newLoanBalance);

        if (newLoanBalance.compareTo(BigDecimal.ZERO) == 0) {
            loan.setStatus(LoanStatus.SETTLED);
        }
        
        loanRepository.save(loan);
        return paymentRepository.save(payment);
    }

    
}
