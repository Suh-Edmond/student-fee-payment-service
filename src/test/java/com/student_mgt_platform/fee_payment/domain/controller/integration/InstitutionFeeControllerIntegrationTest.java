package com.student_mgt_platform.fee_payment.domain.controller.integration;

import com.student_mgt_platform.fee_payment.FeePaymentApplication;
import com.student_mgt_platform.fee_payment.constant.InstitutionalFeeCategory;
import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import com.student_mgt_platform.fee_payment.domain.repository.InstitutionalFeeRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FeePaymentApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class InstitutionFeeControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InstitutionalFeeRepository institutionalFeeRepository;

    @BeforeEach
    void setUp() {
        InstitutionalFee institutionalFee = new InstitutionalFee();
        institutionalFee.setCategory(InstitutionalFeeCategory.FRESH_MEN);
        institutionalFee.setName("Fresh Men");
        institutionalFee.setAmountPayable(BigDecimal.valueOf(800000));

    }

    @AfterEach
    void tearDown() {
        institutionalFeeRepository.deleteAll();
    }

    @Test
    void getInstitutionalFees() throws Exception {
        mockMvc.perform(get("/api/public/institution-fees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
