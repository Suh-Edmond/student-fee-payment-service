package com.student_mgt_platform.fee_payment.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.student_mgt_platform.fee_payment.util.PaymentDateScheduler.computeNextPaymentDueDate;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentDateSchedulerTest {
    private final List<DayOfWeek> WEEK_DAYS = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);

    @Test
    void computeNextPaymentDueDate_should_return_next_payment_due_date_when_due_date_is_weekday() {
        LocalDate currentDate = LocalDate.of(2026, 3, 14);
        LocalDate nextPaymentDueDate = computeNextPaymentDueDate(currentDate);

        assertTrue(WEEK_DAYS.contains(nextPaymentDueDate.getDayOfWeek()));
        assertEquals(DayOfWeek.FRIDAY, nextPaymentDueDate.getDayOfWeek());
    }

    @Test
    void computeNextPaymentDueDate_should_reset_due_when_due_date_is_saturday() {
        LocalDate currentDate = LocalDate.of(2026, 4, 6);
        LocalDate nextPaymentDueDate = computeNextPaymentDueDate(currentDate);

        assertTrue(WEEK_DAYS.contains(nextPaymentDueDate.getDayOfWeek()));
        assertEquals(DayOfWeek.MONDAY, nextPaymentDueDate.getDayOfWeek());
    }

    @Test
    void computeNextPaymentDueDate_should_reset_due_when_due_date_is_sunday() {
        LocalDate currentDate = LocalDate.of(2026, 4, 6);
        LocalDate nextPaymentDueDate = computeNextPaymentDueDate(currentDate);

        assertTrue(WEEK_DAYS.contains(nextPaymentDueDate.getDayOfWeek()));
        assertEquals(DayOfWeek.MONDAY, nextPaymentDueDate.getDayOfWeek());
    }

}