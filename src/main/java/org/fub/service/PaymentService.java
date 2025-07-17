package org.fub.service;

import jakarta.validation.constraints.NotNull;
import org.fub.request.PaymentDetail;
import org.fub.request.PaymentDetailRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {
    PaymentDetail createPaymentDetails(PaymentDetailRequest paymentDetail);

    PaymentDetail fetchPaymentDetail(String userId, String paymentId, Long crewId);

    boolean deletePaymentDetail(String userId, @NotNull String paymentId);

    PaymentDetail updatePaymentDetails(String userId, String paymentId,Long crewId, PaymentDetail request);

    boolean generatePaymentDetailsForMembers(Long crewId);

    List<PaymentDetail> fetchPaymentsDetails(String userId, @NotNull Long crewId);
}
