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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static com.student_mgt_platform.fee_payment.util.PaymentDateScheduler.computeNextPaymentDueDate;
import static com.student_mgt_platform.fee_payment.util.PaymentIncentiveCalculator.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeePaymentServiceImpl implements FeePaymentService{
    private final FeePaymentRepository feePaymentRepository;
    private final InstitutionalFeeRepository institutionalFeeRepository;
    private final StudentAccServiceImpl studentAccService;

    @Override
    @Transactional
    public FeePaymentDto makeStudentFeePayment(FeePaymentRequest request) {
        InstitutionalFee institutionalFee = getInstitutionalFee(request.getInstitutionalFeeCategory());
        Optional<StudentAccount> stud = studentAccService.getStudentAccount(request.getStudentNumber());
        StudentAccount studentAccount = stud.orElseGet(() -> studentAccService.createStudentAccount(request.getStudentNumber(), institutionalFee));
        Optional<FeePayment> studentLatestFeePayment = getStudentLatestFeePayment(studentAccount.getId());

        int incentiveRate = computeIncentiveRate(request.getPaymentAmount());
        BigDecimal incentiveAmount = computeIncentiveAmount(request.getPaymentAmount(), incentiveRate);

        LocalDate paymentDate = LocalDate.now();

        LocalDate nextPaymentDueDate = computeNextPaymentDueDate(paymentDate);
        BigDecimal balancePayment = studentLatestFeePayment.isEmpty() ? studentAccount.getCurrentBalance() : studentLatestFeePayment.get().getNewBalance();
        BigDecimal paymentBalance = computePaymentBalance(request.getPaymentAmount(), incentiveAmount, balancePayment);

        FeePayment feePayment = new FeePayment();
        feePayment.setPreviousBalance(balancePayment);
        feePayment.setStudentAccount(studentAccount);
        feePayment.setPaymentAmount(request.getPaymentAmount());
        feePayment.setIncentiveAmount(incentiveAmount);
        feePayment.setIncentiveRate(incentiveRate);
        feePayment.setPaymentDate(paymentDate);
        feePayment.setNewBalance(paymentBalance);

        FeePayment savedFeePayment = feePaymentRepository.save(feePayment);

        studentAccount.setNextDueDate(nextPaymentDueDate);
        studentAccount.setCurrentBalance(paymentBalance);
        studentAccService.updateStudentAccount(studentAccount);

        return FeePaymentMapper.INSTANCE.feePaymentDtoToFeePayment(savedFeePayment, nextPaymentDueDate);

    }

    private InstitutionalFee getInstitutionalFee(InstitutionalFeeCategory category){
        Optional<InstitutionalFee> institutionalFee = institutionalFeeRepository.findInstitutionalFeeByCategory(category);
        if(institutionalFee.isEmpty()){
            throw new BusinessValidationException("Institutional fee not found");
        }
        return institutionalFee.get();
    }

    private Optional<FeePayment> getStudentLatestFeePayment(UUID studentId){
        return feePaymentRepository.findFirstByStudentAccount_IdOrderByPaymentDateDesc(studentId);
    }
    
}
