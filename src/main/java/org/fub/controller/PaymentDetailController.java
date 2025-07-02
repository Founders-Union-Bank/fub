package org.fub.controller;

import lombok.AllArgsConstructor;
import org.fub.controller.API.PaymentDetailAPI;
import org.fub.model.PaymentDetail;
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
        return new ResponseEntity<PaymentDetail>(detail, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<PaymentDetail>> fetchPaymentsDetail(String userId) {
        List<PaymentDetail> details = service.fetchPaymentsDetail(userId);
        return new ResponseEntity<List<PaymentDetail>>(details, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deletePaymentDetails(String userId, String paymentId) {
        boolean isDeleted = service.deletePaymentDetails(userId, paymentId);
        if (isDeleted) {
            return ResponseEntity.ok("Payment Details Deleted successfully");
        } else {
            return new ResponseEntity<>("Payment details doesn't exist", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<PaymentDetail> fetchPaymentDetails(String userId, String paymentId) {
        PaymentDetail paymentDetail = service.fetchPaymentDetails(userId, paymentId);
        return new ResponseEntity<PaymentDetail>(paymentDetail, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PaymentDetail> updatePaymentDetails(String userId, String paymentId, PaymentDetailRequest request) {
        PaymentDetail paymentDetail = service.updatePaymentDetails(userId, paymentId, request);
        return new ResponseEntity<PaymentDetail>(paymentDetail, HttpStatus.OK);
    }
}
