package org.fub.service.impl;

import lombok.AllArgsConstructor;
import org.fub.model.PaymentDetail;
import org.fub.repository.PaymentRepository;
import org.fub.request.PaymentDetailRequest;
import org.fub.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository repository;

    private ModelMapper mapper;

    @Override
    public PaymentDetail createPaymentDetails(PaymentDetailRequest paymentDetail) {
        PaymentDetail detail = mapper.map(paymentDetail, PaymentDetail.class);
        detail.setAmountDetailId(UUID.randomUUID().toString());
        detail.setPaymentDate(new Date());

        PaymentDetail savedPayment = repository.save(detail);

        if (savedPayment == null) {
            throw new RuntimeException("Payment saved failed");
        }
        return savedPayment;
    }

    @Override
    public PaymentDetail fetchPaymentDetails(String userId, String paymentId) {
        Optional<PaymentDetail> paymentDetail = repository.findById(paymentId);
        if (paymentDetail.isPresent()) {
            return paymentDetail.get();
        } else {
            throw new RuntimeException("Payment Details doesn't exist");
        }
    }

    @Override
    public boolean deletePaymentDetails(String userId, String paymentId) {
        Optional<PaymentDetail> paymentDetail = repository.findById(paymentId);
        if (paymentDetail.isPresent()) {
            return repository.deleteByAmountDetailId(paymentId);
        } else {
            throw new RuntimeException("Payment Details doesn't exist");
        }
    }

    @Override
    public List<PaymentDetail> fetchPaymentsDetail(String userId) {
        List<PaymentDetail> details = repository.findAllByUserId(userId);
        if (details.isEmpty()) {
            throw new RuntimeException("Payment Details doesn't exist");
        }
        return details;
    }

    @Override
    public PaymentDetail updatePaymentDetails(String userId, String paymentId, PaymentDetailRequest request) {
        Optional<PaymentDetail> paymentDetail = repository.findById(paymentId);
        if (paymentDetail.isPresent()) {
            PaymentDetail payment = paymentDetail.get();
            payment.setPaymentStatus(request.getPaymentStatus());
            payment.setBillingMonth(request.getBillingMonth());
            payment.setContributionAmount(request.getContributionAmount());
            return repository.save(payment);
        } else {
            throw new RuntimeException("Payment Details doesn't exist");
        }
    }
}
