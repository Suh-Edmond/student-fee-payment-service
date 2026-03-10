package com.student_mgt_platform.fee_payment.domain.repository;

import com.student_mgt_platform.fee_payment.constant.InstitutionalFeeCategory;
import com.student_mgt_platform.fee_payment.domain.model.FeePayment;
import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstitutionalFeeRepository extends JpaRepository<InstitutionalFee, UUID> {
     Optional<InstitutionalFee> findInstitutionalFeeByCategory(InstitutionalFeeCategory category);
}
