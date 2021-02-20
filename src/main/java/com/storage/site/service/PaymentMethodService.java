package com.storage.site.service;

import com.storage.site.dao.PaymentMethodDao;
import com.storage.site.model.PaymentMethod;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodDao paymentMethodDao;

    public PaymentMethod getPaymentMethodById(int id) {
        return paymentMethodDao.getPaymentMethodById(id);
    }

    public List<PaymentMethod> getPaymentMethodsByCustomerId(int customerId) {
        return paymentMethodDao.getPaymentMethodsByCustomerId(customerId);
    }

    public void save(PaymentMethod paymentMethod) {
        paymentMethodDao.insert(paymentMethod);
    }

    public void delete(Long id) {
        paymentMethodDao.deleteById(id);
    }

}
