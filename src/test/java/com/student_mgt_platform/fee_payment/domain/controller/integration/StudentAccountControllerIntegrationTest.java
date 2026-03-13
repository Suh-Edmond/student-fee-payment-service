package com.student_mgt_platform.fee_payment.domain.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student_mgt_platform.fee_payment.FeePaymentApplication;
import com.student_mgt_platform.fee_payment.constant.InstitutionalFeeCategory;
import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import com.student_mgt_platform.fee_payment.domain.repository.InstitutionalFeeRepository;
import com.student_mgt_platform.fee_payment.domain.repository.StudentAccountRepository;
import com.student_mgt_platform.fee_payment.dto.StudentAccountRequestDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FeePaymentApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StudentAccountControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentAccountRepository studentAccountRepository;

    @Autowired
    private InstitutionalFeeRepository institutionalFeeRepository;

    private StudentAccountRequestDto studentAccountRequestDto;

    @BeforeEach
    void setUp() {
        InstitutionalFee institutionalFee = new InstitutionalFee();
        institutionalFee.setCategory(InstitutionalFeeCategory.FRESH_MEN);
        institutionalFee.setName("Fresh Men");
        institutionalFee.setAmountPayable(BigDecimal.valueOf(800000));
        institutionalFeeRepository.save(institutionalFee);

        studentAccountRequestDto = new StudentAccountRequestDto();
        studentAccountRequestDto.setStudentNumber("studentNumber");
        studentAccountRequestDto.setStudentName("studentName");
        studentAccountRequestDto.setInstitutionFeeId(institutionalFee.getId().toString());

    }

    @AfterEach
    void tearDown() {
        studentAccountRepository.deleteAll();
        institutionalFeeRepository.deleteAll();
    }

    @Test
    void createStudentAccount_returns_200() throws Exception {
        mockMvc.perform(post("/api/public/student-account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(studentAccountRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentNumber").value("studentNumber"))
                .andExpect(jsonPath("$.studentName").value("studentName"));
    }

    @Test
    void createStudentAccount_returns_400() throws Exception {
        studentAccountRequestDto.setStudentNumber("");
        mockMvc.perform(post("/api/public/student-account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(studentAccountRequestDto)))
                .andExpect(status().isBadRequest());
    }
}
