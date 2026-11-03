package com.ssvm.school.repository;

import com.ssvm.school.entity.FeeDue;
import com.ssvm.school.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeePaymentRepository extends JpaRepository<FeePayment, String> {

    long countByFeeDue(FeeDue feeDue);

    List<FeePayment> findByFeeDue(FeeDue feeDue);

}
