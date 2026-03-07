package com.domain.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.java.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}