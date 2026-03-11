package com.student_mgt_platform.fee_payment.util;

import java.math.BigDecimal;

import static com.student_mgt_platform.fee_payment.constant.IncentiveRate.*;

public class PaymentIncentiveCalculator {
    public static int computeIncentiveRate(BigDecimal amount) {
        if (isBetween(amount, "0", "100000")) {
            return MINIMUM_AMOUNT;
        } else if (isBetween(amount, "100000", "500000")) {
            return MEDIUM_AMOUNT;
        } else if (amount.compareTo(new BigDecimal("500000")) > 0) {
            return MAXIMUM_AMOUNT;
        }
        return 0;
    }

    public static BigDecimal computeIncentiveAmount(BigDecimal paymentAmount) {
        int incentiveRate = computeIncentiveRate(paymentAmount);
        return paymentAmount.multiply(BigDecimal.valueOf(incentiveRate));
    }

    public static BigDecimal computePaymentBalance(BigDecimal paymentAmount, BigDecimal incentiveAmount, BigDecimal institutionalFee) {
        return institutionalFee.subtract(paymentAmount.add(incentiveAmount));
    }

    private static boolean isBetween(BigDecimal val, String start, String end) {
        return val.compareTo(new BigDecimal(start)) >= 0
                && val.compareTo(new BigDecimal(end)) <= 0;
    }
}
