package com.byritium.dao;

import com.byritium.entity.PaymentOrder;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = PaymentOrder.class, idClass = String.class)
public interface PaymentOrderRepository {

    PaymentOrder save(PaymentOrder paymentOrder);
}
