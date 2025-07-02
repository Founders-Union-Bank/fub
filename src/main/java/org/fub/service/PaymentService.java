package org.fub.service;

import jakarta.validation.constraints.NotNull;
import org.fub.model.PaymentDetail;
import org.fub.request.PaymentDetailRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {
    PaymentDetail createPaymentDetails(PaymentDetailRequest paymentDetail);

    PaymentDetail fetchPaymentDetails(String userId, String paymentId);

    boolean deletePaymentDetails(String userId, @NotNull String paymentId);

    List<PaymentDetail> fetchPaymentsDetail(String userId);

    PaymentDetail updatePaymentDetails(String userId, String paymentId, PaymentDetailRequest request);
}
