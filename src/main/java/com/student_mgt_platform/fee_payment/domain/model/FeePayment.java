package com.student_mgt_platform.fee_payment.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class FeePayment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "previous_balance")
    private BigDecimal previousBalance;

    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;

    @Column(name = "incentive_rate")
    private int incentiveRate;

    @Column(name = "incentive_amount")
    private BigDecimal incentiveAmount;

    @Column(name = "new_balance")
    private BigDecimal newBalance;

    @Column(name = "next_due_date")
    private LocalDate nextDueDate;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @ManyToOne()
    private StudentAccount studentAccount;
}
