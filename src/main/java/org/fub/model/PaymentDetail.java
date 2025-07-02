package org.fub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "payment_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetail {
    @Id
    @Column(name = "amount_details_id")
    private String amountDetailId;
    @Column(name = "userId", nullable = false)
    @NotNull
    private String userId;
    @NotNull
    @Column(name = "contribution_amount",nullable = false)
    private Double contributionAmount;
    @NotNull
    @Column(name = "payment_status",nullable = false)
    private String paymentStatus;
    @NotNull
    @Column(name = "billing_month",nullable = false)
    private Date billingMonth;
    @NotNull
    @Column(name = "paymentDate",nullable = false)
    private Date paymentDate;
    @Column(name = "paymentProof")
    private byte[] proofData;

}
