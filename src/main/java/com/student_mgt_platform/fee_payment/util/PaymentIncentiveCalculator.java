package com.student_mgt_platform.fee_payment.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.student_mgt_platform.fee_payment.constant.IncentiveRate.*;


public class PaymentIncentiveCalculator {
    public static int computeIncentiveRate(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0 && amount.compareTo(BigDecimal.valueOf(100000)) < 0) {
            return  MINIMUM_RATE;
        } else if (amount.compareTo(BigDecimal.valueOf(100000)) >= 0 && amount.compareTo(BigDecimal.valueOf(500000)) < 0) {
            return  MEDIUM_RATE;
        } else if (amount.compareTo(BigDecimal.valueOf(500000)) >= 0) {
            return MAXIMUM_RATE;
        }
        return  0;
    }

    public static BigDecimal computeIncentiveAmount(BigDecimal paymentAmount,  int incentiveRate) {
        double rate = (double) incentiveRate / 100;
        return paymentAmount.multiply(BigDecimal.valueOf(rate)).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal computePaymentBalance(BigDecimal paymentAmount, BigDecimal incentiveAmount, BigDecimal institutionalFee) {
        return institutionalFee.subtract(paymentAmount.add(incentiveAmount)).setScale(2, RoundingMode.HALF_UP);
    }

}
