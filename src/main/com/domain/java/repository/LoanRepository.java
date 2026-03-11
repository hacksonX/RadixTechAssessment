package com.domain.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.java.models.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    

}