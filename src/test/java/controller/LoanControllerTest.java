package com.domain.java.loan.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.domain.java.enums.LoanStatus;
import com.domain.java.loan.service.LoanService;
import com.domain.java.models.Loan;


@WebMvcTest(LoanController.class)
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createLoan_ShouldReturnCreatedLoan() throws Exception {
        Loan loan = new Loan();
        loan.setLoanId(1L);
        loan.setLoanAmount(new BigDecimal("1000.00"));
        loan.setTerm(12);
        loan.setStatus(LoanStatus.ACTIVE);


        when(loanService.createLoan(any(Loan.class))).thenReturn(loan);

        mockMvc.perform(post("/api/v1/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanId").value(1L))
                .andExpect(jsonPath("$.loanAmount").value(1000.00))
                .andExpect(jsonPath("$.status").value(LoanStatus.ACTIVE.name()))
                .andExpect(jsonPath("$.term").value(12));
    }

    @Test
    void getLoanById_ShouldReturnLoan_WhenFound() throws Exception {
        Loan loan = new Loan();
        loan.setLoanId(1L);
        loan.setLoanAmount(new BigDecimal("500.00"));

        when(loanService.getLoanById(1L)).thenReturn(loan);

        mockMvc.perform(get("/api/v1/loan/{loanId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanId").value(1L))
                .andExpect(jsonPath("$.loanAmount").value(500.00));
    }
}