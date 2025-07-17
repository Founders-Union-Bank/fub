package org.fub.controller;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.fub.controller.API.PaymentDetailAPI;
import org.fub.request.PaymentDetail;
import org.fub.request.PaymentDetailRequest;
import org.fub.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PaymentDetailController implements PaymentDetailAPI {

    private PaymentService service;

    @Override
    public ResponseEntity<PaymentDetail> createPayment(PaymentDetailRequest paymentDetail) {
        PaymentDetail detail = service.createPaymentDetails(paymentDetail);
        return new ResponseEntity<>(detail, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<PaymentDetail>> fetchPaymentsDetails(String userId, Long crewId) {
        List<PaymentDetail> details = service.fetchPaymentsDetails(userId, crewId);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deletePaymentDetails(String userId, String paymentId) {
        boolean isDeleted = service.deletePaymentDetail(userId, paymentId);
        if (isDeleted) {
            return ResponseEntity.ok("Payment Details Deleted successfully");
        } else {
            return new ResponseEntity<>("Payment details doesn't exist", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<PaymentDetail> fetchPaymentDetails(String userId, String paymentId, Long crewId) {
        PaymentDetail paymentDetail = service.fetchPaymentDetail(userId, paymentId, crewId);
        return new ResponseEntity<>(paymentDetail, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PaymentDetail> updatePaymentDetails(String userId, String paymentId, Long crewId, PaymentDetail paymentDetail) {
        PaymentDetail payment = service.updatePaymentDetails(userId, paymentId,crewId ,paymentDetail);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> generatePaymentDetailsForMembers(Long crewId) {
        boolean isSuccess = service.generatePaymentDetailsForMembers(crewId);
        return new ResponseEntity<>("Payment Generated Successfully", HttpStatus.OK);
    }
}
