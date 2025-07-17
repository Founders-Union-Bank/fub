package org.fub.repository;

import org.fub.model.PaymentDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDetailModel, String> {

    Optional<PaymentDetailModel> findByPaymentIdAndUserIdAndCrewId( String paymentId,String userId, Long crewId);

    int deleteByPaymentId(String id);

    List<PaymentDetailModel> findAllByUserIdAndCrewId(String userId, Long crewId);
}
