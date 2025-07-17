package org.fub.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.fub.model.PaymentDetailModel;
import org.fub.repository.PaymentRepository;
import org.fub.request.PaymentDetail;
import org.fub.request.PaymentDetailRequest;
import org.fub.response.UserResponse;
import org.fub.service.GroupService;
import org.fub.service.PaymentService;
import org.fub.utils.PaymentStatus;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository repository;

    private ModelMapper mapper;

    private GroupService groupService;

    @Override
    public PaymentDetail createPaymentDetails(PaymentDetailRequest paymentDetail) {
        PaymentDetailModel detail = mapper.map(paymentDetail, PaymentDetailModel.class);
        detail.setPaymentId(UUID.randomUUID().toString());
        detail.setPaymentDate(new Date());

        PaymentDetailModel savedPayment = repository.save(detail);

        if (savedPayment == null) {
            throw new RuntimeException("Payment saved failed");
        }
        return mapper.map(savedPayment, PaymentDetail.class);
    }

    @Override
    public PaymentDetail fetchPaymentDetail(String userId, String paymentId, Long crewId) {
        PaymentDetailModel paymentDetailModel = repository.findByPaymentIdAndUserIdAndCrewId(paymentId, userId, crewId).orElseThrow(() -> new RuntimeException("Payment Details doesn't exist"));
        return mapper.map(paymentDetailModel, PaymentDetail.class);
    }

    @Transactional
    @Modifying
    @Override
    public boolean deletePaymentDetail(String userId, String paymentId) {
        PaymentDetailModel paymentDetail = repository.findById(paymentId).orElseThrow(() -> new RuntimeException("Payment Details doesn't exist"));
        return repository.deleteByPaymentId(paymentId) == 1;
    }

    @Override
    public PaymentDetail updatePaymentDetails(String userId, String paymentId, Long crewId, PaymentDetail request) {
        PaymentDetailModel payment = repository.findByPaymentIdAndUserIdAndCrewId(paymentId, userId, crewId).orElseThrow(() -> new RuntimeException("Payment Details doesn't exist"));
        if (request.getPaymentStatus().equals("COMPLETED")) {
            payment.setPaymentStatus(PaymentStatus.COMPLETED.getName());
        }
        payment.setPaymentDate(new Date());
        payment.setContributionAmount(request.getContributionAmount());
        return mapper.map(repository.save(payment), PaymentDetail.class);
    }

    @Override
    public boolean generatePaymentDetailsForMembers(Long crewId) {
        List<UserResponse> users = groupService.getAllUsersFromTheCrew(crewId);
        AtomicInteger result = new AtomicInteger();
        users.forEach(user -> {
            PaymentDetailModel paymentDetail = new PaymentDetailModel();
            paymentDetail.setPaymentId(UUID.randomUUID().toString());
            paymentDetail.setContributionAmount(2000D);
            paymentDetail.setCrewId(crewId);
            paymentDetail.setUserId(user.getUserId());
            paymentDetail.setPaymentDate(new Date());
            paymentDetail.setBillingMonth(new Date());
            paymentDetail.setPaymentStatus(PaymentStatus.PENDING.getName());
            repository.save(paymentDetail);
            result.getAndIncrement();
        });
        return users.size() == result.get();
    }

    @Override
    public List<PaymentDetail> fetchPaymentsDetails(String userId, Long crewId) {
        List<PaymentDetailModel> payments = repository.findAllByUserIdAndCrewId(userId, crewId);
        return payments.stream().map(payment -> mapper.map(payment, PaymentDetail.class)).toList();
    }
}
