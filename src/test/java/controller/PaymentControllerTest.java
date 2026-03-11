package com.domain.java.payment.controller;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import com.domain.java.models.Payment;
import com.domain.java.payment.service.PaymentService;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void processPayment_ShouldReturnProcessedPayment() throws Exception {
        Payment payment = new Payment();
        payment.setPaymentId(1L);
        payment.setLoanId(1L);
        payment.setPaymentAmount(new BigDecimal("100.00"));

        when(paymentService.processPayment(any(Payment.class))).thenReturn(payment);
        
        mockMvc.perform(post("/api/v1/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(1L))
                .andExpect(jsonPath("$.loanId").value(1L))
                .andExpect(jsonPath("$.paymentAmount").value(100.00));
    }

    @Test
    void processPayment_ShouldReturnBadRequest_WhenInvalidPayment() throws Exception {
        Payment payment = new Payment();
        payment.setPaymentId(1L);
        payment.setLoanId(1L);
        payment.setPaymentAmount(new BigDecimal("0.00"));

        when(paymentService.processPayment(any(Payment.class))).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(post("/api/v1/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payment))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
    
    
}
