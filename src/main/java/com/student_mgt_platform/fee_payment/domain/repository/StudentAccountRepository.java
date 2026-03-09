package com.student_mgt_platform.fee_payment.domain.repository;

import com.student_mgt_platform.fee_payment.domain.model.StudentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentAccountRepository extends JpaRepository<StudentAccount, UUID> {
    Optional<StudentAccount> findByStudentNumber(String studentNumber);
}
