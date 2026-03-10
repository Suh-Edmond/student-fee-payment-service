package com.student_mgt_platform.fee_payment.util;

import java.math.BigDecimal;

import static com.student_mgt_platform.fee_payment.constant.IncentiveRate.*;

public class PaymentIncentiveCalculator {

    public static int computeIncentiveRate(BigDecimal paymentAmount) {
        return switch (paymentAmount) {
            case BigDecimal i when i.compareTo(BigDecimal.ZERO) < 1 && i.compareTo(BigDecimal.valueOf(100000)) > -1 -> MINIMUM_AMOUNT;
            case BigDecimal i when i.compareTo(BigDecimal.valueOf(100000)) > -1 && i.compareTo(BigDecimal.valueOf(500000)) > -1 -> MEDIUM_AMOUNT;
            case BigDecimal i when i.compareTo(BigDecimal.valueOf(500000)) > -1 -> MAXIMUM_AMOUNT;
            default -> 0;
        };
    }

    public static BigDecimal computeIncentiveAmount(BigDecimal paymentAmount) {
        int incentiveRate = computeIncentiveRate(paymentAmount);

        return paymentAmount.multiply(BigDecimal.valueOf(incentiveRate));
    }

    public static BigDecimal computePaymentBalance(BigDecimal paymentAmount, BigDecimal incentiveAmount, BigDecimal institutionalFee) {
        return institutionalFee.subtract(paymentAmount.add(incentiveAmount));
    }
}
