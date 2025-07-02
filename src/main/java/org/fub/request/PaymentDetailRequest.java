package org.fub.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
public class PaymentDetailRequest {

    private String userId;

    private Double contributionAmount;

    private String paymentStatus;

    private Date billingMonth;

}
