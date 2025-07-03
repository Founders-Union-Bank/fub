package org.fub.repository;

import org.fub.model.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDetail,String> {

    int deleteByAmountDetailId(String id);

    List<PaymentDetail> findAllByUserId(String userId);
}
