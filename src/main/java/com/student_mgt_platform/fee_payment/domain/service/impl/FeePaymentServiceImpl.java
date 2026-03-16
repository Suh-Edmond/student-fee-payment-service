package com.student_mgt_platform.fee_payment.domain.service.impl;

import com.student_mgt_platform.fee_payment.domain.model.FeePayment;
import com.student_mgt_platform.fee_payment.domain.model.StudentAccount;
import com.student_mgt_platform.fee_payment.domain.repository.FeePaymentRepository;
import com.student_mgt_platform.fee_payment.domain.service.FeePaymentService;
import com.student_mgt_platform.fee_payment.dto.FeePaymentDto;
import com.student_mgt_platform.fee_payment.dto.FeePaymentRequest;
import com.student_mgt_platform.fee_payment.dto.mapper.FeePaymentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.student_mgt_platform.fee_payment.util.PaymentDateScheduler.computeNextPaymentDueDate;
import static com.student_mgt_platform.fee_payment.util.PaymentIncentiveCalculator.*;


@Service
@RequiredArgsConstructor
public class FeePaymentServiceImpl implements FeePaymentService {
    private final FeePaymentRepository feePaymentRepository;
    private final StudentAccServiceImpl studentAccService;

    @Override
    @Transactional
    public FeePaymentDto makeStudentFeePayment(FeePaymentRequest request) {
        StudentAccount studentAccount = studentAccService.getStudentAccount(request.getStudentNumber());
        Optional<FeePayment> studentLatestFeePayment = getStudentLatestFeePayment(studentAccount.getId());

        int incentiveRate = computeIncentiveRate(request.getPaymentAmount());
        BigDecimal incentiveAmount = computeIncentiveAmount(request.getPaymentAmount(), incentiveRate);

        LocalDate paymentDate = LocalDate.now();

        LocalDate nextPaymentDueDate = computeNextPaymentDueDate(paymentDate);
        BigDecimal balancePayment = studentLatestFeePayment.isEmpty() ? studentAccount.getInstitutionalFee().getAmountPayable() : studentLatestFeePayment.get().getNewBalance();
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
        studentAccService.updateStudentAccount(studentAccount);

        return FeePaymentMapper.INSTANCE.feePaymentToFeePaymentDto(savedFeePayment, nextPaymentDueDate);
    }

    @Override
    public List<FeePaymentDto> getStudentPayments(String studentNumber) {
        List<FeePayment> studentPayments = feePaymentRepository.findAllByStudentAccount_StudentNumberOrderByPaymentDateDesc(studentNumber);
        return studentPayments.stream().map(e -> FeePaymentMapper.INSTANCE.feePaymentToFeePaymentDto(e, e.getStudentAccount().getNextDueDate())).toList();
    }

    private Optional<FeePayment> getStudentLatestFeePayment(UUID studentId) {
        return feePaymentRepository.findFirstByStudentAccount_IdOrderByPaymentDateDesc(studentId);
    }

}
