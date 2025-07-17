package org.fub.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PaymentDetail {

    private String paymentId;

    private String userId;

    private Double contributionAmount;

    private String paymentStatus;

    private Date billingMonth;

    private Date paymentDate;

    private Long groupId;
}
