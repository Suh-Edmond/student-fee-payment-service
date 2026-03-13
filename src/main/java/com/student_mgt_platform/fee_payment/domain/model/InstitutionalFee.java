package com.student_mgt_platform.fee_payment.domain.model;

import com.student_mgt_platform.fee_payment.constant.InstitutionalFeeCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;


@Table(name = "institutional_fees")
@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class InstitutionalFee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private InstitutionalFeeCategory category;

    @Column(name = "amount_payable")
    private BigDecimal amountPayable;
}
