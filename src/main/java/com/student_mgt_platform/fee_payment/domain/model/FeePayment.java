package com.student_mgt_platform.fee_payment.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class FeePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
