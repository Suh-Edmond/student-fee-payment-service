package com.student_mgt_platform.fee_payment.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.student_mgt_platform.fee_payment.util.PaymentIncentiveCalculator.computeIncentiveRate;
import static com.student_mgt_platform.fee_payment.util.PaymentIncentiveCalculator.computeIncentiveAmount;
import static com.student_mgt_platform.fee_payment.util.PaymentIncentiveCalculator.computePaymentBalance;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentIncentiveCalculatorTest {

    @Test
    void computeIncentiveRate_when_amount_is_zero() {
        int incentiveRate = computeIncentiveRate(BigDecimal.valueOf(0));
        assertEquals(0, incentiveRate);
    }

    @Test
    void computeIncentiveRate_when_amount_is_less_than_equal_100000() {
        int incentiveRate = computeIncentiveRate(BigDecimal.valueOf(100000));
        assertEquals(3, incentiveRate);
    }

    @Test
    void computeIncentiveRate_when_amount_is_greater_or_equal_100000_less_than_500000() {
        int incentiveRate = computeIncentiveRate(BigDecimal.valueOf(300000));
        assertEquals(3, incentiveRate);
    }

    @Test
    void computeIncentiveRate_when_amount_is_greater_than_equal_500000() {
        int incentiveRate = computeIncentiveRate(BigDecimal.valueOf(500000));
        assertEquals(5, incentiveRate);
    }

    @Test
    void computeIncentiveAmount_should_compute_incentiveAmount() {
        BigDecimal incentiveAmount = computeIncentiveAmount(BigDecimal.valueOf(100000), 3);
        assertEquals(BigDecimal.valueOf(300000), incentiveAmount);
    }

    @Test
    void computePaymentBalance_should_compute_paymentBalance() {
        BigDecimal paymentBalance = computePaymentBalance(BigDecimal.valueOf(100000), BigDecimal.valueOf(3000), BigDecimal.valueOf(800000));
        assertEquals(BigDecimal.valueOf(697000), paymentBalance);
    }
}