package com.student_mgt_platform.fee_payment.domain.service;

import com.student_mgt_platform.fee_payment.constant.InstitutionalFeeCategory;
import com.student_mgt_platform.fee_payment.domain.exceptions.BusinessValidationException;
import com.student_mgt_platform.fee_payment.domain.model.FeePayment;
import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import com.student_mgt_platform.fee_payment.domain.model.StudentAccount;
import com.student_mgt_platform.fee_payment.domain.repository.FeePaymentRepository;
import com.student_mgt_platform.fee_payment.domain.repository.InstitutionalFeeRepository;
import com.student_mgt_platform.fee_payment.dto.FeePaymentDto;
import com.student_mgt_platform.fee_payment.dto.FeePaymentRequest;
import com.student_mgt_platform.fee_payment.dto.mapper.FeePaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.student_mgt_platform.fee_payment.util.PaymentDateScheduler.computeNextPaymentDueDate;
import static com.student_mgt_platform.fee_payment.util.PaymentIncentiveCalculator.*;

@Service
@RequiredArgsConstructor
public class FeePaymentServiceImpl implements FeePaymentService{
    private final FeePaymentRepository feePaymentRepository;
    private final InstitutionalFeeRepository InstitutionalFeeRepository;
    private final StudentAccServiceImpl studentAccService;

    @Override
    public FeePaymentDto makeStudentFeePayment(FeePaymentRequest request) {
        StudentAccount studentAccount = new StudentAccount();
        InstitutionalFee institutionalFee = getInstitutionalFee(request.getInstitutionalFeeCategory());
        Optional<StudentAccount> studAccount = studentAccService.getStudentAccount(request.getStudentNumber());
        if(studAccount.isEmpty()){
            studentAccount = studentAccService.createStudentAccount(request.getStudentNumber());
        }
        int incentiveRate = computeIncentiveRate(request.getPaymentAmount());
        BigDecimal incentiveAmount = computeIncentiveAmount(request.getPaymentAmount());
        LocalDate paymentDate = LocalDate.now();
        LocalDate nextPaymentDueDate = computeNextPaymentDueDate(paymentDate);
        BigDecimal paymentBalance = computePaymentBalance(request.getPaymentAmount(), incentiveAmount, institutionalFee.getAmountPayable());

        FeePayment feePayment = new FeePayment();
        feePayment.setStudentAccount(studentAccount);
        feePayment.setPaymentAmount(request.getPaymentAmount());
        feePayment.setIncentiveAmount(incentiveAmount);
        feePayment.setIncentiveRate(incentiveRate);
        feePayment.setPaymentDate(paymentDate);
        feePayment.setNextDueDate(nextPaymentDueDate);
        feePayment.setNewBalance(paymentBalance);

        FeePayment savedFeePayment = feePaymentRepository.save(feePayment);

        return FeePaymentMapper.INSTANCE.feePaymentDtoToFeePayment(savedFeePayment);
    }

    private InstitutionalFee getInstitutionalFee(InstitutionalFeeCategory category){
        Optional<InstitutionalFee> institutionalFee = InstitutionalFeeRepository.findInstitutionalFeeByCategory(category);
        if(institutionalFee.isEmpty()){
            throw new BusinessValidationException("Institutional fee not found");
        }
        return institutionalFee.get();
    }
}
