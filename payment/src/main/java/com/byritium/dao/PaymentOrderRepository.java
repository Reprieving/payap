package com.byritium.dao;

import com.byritium.service.callback.entity.PaymentOrder;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = PaymentOrder.class, idClass = String.class)
public interface PaymentOrderRepository {

    PaymentOrder save(PaymentOrder paymentOrder);
}
