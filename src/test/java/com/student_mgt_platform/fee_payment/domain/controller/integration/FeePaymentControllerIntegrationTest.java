package com.student_mgt_platform.fee_payment.domain.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student_mgt_platform.fee_payment.FeePaymentApplication;
import com.student_mgt_platform.fee_payment.constant.InstitutionalFeeCategory;
import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import com.student_mgt_platform.fee_payment.domain.model.StudentAccount;
import com.student_mgt_platform.fee_payment.domain.repository.FeePaymentRepository;
import com.student_mgt_platform.fee_payment.domain.repository.InstitutionalFeeRepository;
import com.student_mgt_platform.fee_payment.domain.repository.StudentAccountRepository;
import com.student_mgt_platform.fee_payment.dto.FeePaymentRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FeePaymentApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FeePaymentControllerIntegrationTest {
    @Autowired
    private FeePaymentRepository feePaymentRepository;

    @Autowired
    private StudentAccountRepository studentAccountRepository;

    @Autowired
    private InstitutionalFeeRepository institutionalFeeRepository;

    @Autowired
    private MockMvc mockMvc;

    private FeePaymentRequest feePaymentRequest;


    @BeforeEach
    void setUp() {
        InstitutionalFee institutionalFee = new InstitutionalFee();
        institutionalFee.setName("Institutional Fee");
        institutionalFee.setAmountPayable(BigDecimal.valueOf(800000));
        institutionalFee.setCategory(InstitutionalFeeCategory.FRESH_MEN);
        institutionalFeeRepository.save(institutionalFee);

        StudentAccount studentAccount = new StudentAccount();
        studentAccount.setStudentName("studentName");
        studentAccount.setInstitutionalFee(institutionalFee);
        studentAccount.setStudentNumber("studentNumber");
        studentAccountRepository.save(studentAccount);

        feePaymentRequest = new FeePaymentRequest();
        feePaymentRequest.setPaymentAmount(BigDecimal.valueOf(100000));
        feePaymentRequest.setStudentNumber(studentAccount.getStudentNumber());
    }

    @AfterEach
    void tearDown() {
        feePaymentRepository.deleteAll();
        studentAccountRepository.deleteAll();
        institutionalFeeRepository.deleteAll();
    }

    @Test
    void oneTimeFeePayment_should_make_one_timed_payment_when_student_account_exist() throws Exception{
        mockMvc.perform(post("/api/public/one-time-fee-payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(feePaymentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.previousBalance").value("800000.0"))
                .andExpect(jsonPath("$.incentiveRate").value("3"))
                .andExpect(jsonPath("$.incentiveAmount").value("3000.0"))
                .andExpect(jsonPath("$.newBalance").value("697000.0"))
                .andExpect(jsonPath("$.studentNumber").value("studentNumber"))
                .andExpect(jsonPath("$.paymentAmount").value("100000.0"));
    }

    @Test
    void oneTimeFeePayment_should_return_not_found_when_student_not_account_exist() throws Exception{
        feePaymentRequest.setStudentNumber("notExistingStudentNumber");
        mockMvc.perform(post("/api/public/one-time-fee-payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(feePaymentRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void oneTimeFeePayment_should_bad_request_for_zero_or_negative_amounts() throws Exception{
        feePaymentRequest.setPaymentAmount(BigDecimal.valueOf(-100000));
        mockMvc.perform(post("/api/public/one-time-fee-payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(feePaymentRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getStudentPayments_returns_student_payments() throws Exception{
        mockMvc.perform(get("/api/public/student-payments?studentNumber={studentNumber}", feePaymentRequest.getStudentNumber())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
