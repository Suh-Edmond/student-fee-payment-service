package com.student_mgt_platform.fee_payment.domain.repository;

import com.student_mgt_platform.fee_payment.domain.model.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeePaymentRepository extends JpaRepository<FeePayment, UUID> {
     Optional<FeePayment> findFirstByStudentAccount_IdOrderByPaymentDateDesc(UUID studentAccountId);
     List<FeePayment> findAllByStudentAccount_StudentNumberOrderByPaymentDateDesc(String studentNumber);
}
