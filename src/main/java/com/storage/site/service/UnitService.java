package com.storage.site.service;

import com.storage.site.dao.UnitDao;
import com.storage.site.dto.BookRequest;
import com.storage.site.model.*;
import com.storage.site.util.DateUtil;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class UnitService {

    private final PriceService priceService;
    private final CustomerService customerService;
    private final PaymentMethodService paymentMethodService;
    private final TransactionService transactionService;
    private final UnitDao unitDao;

    public List<Unit> getAllUnits() {
        return unitDao.fetchAll();
    }

    public List<Unit> getUnitsByCustomerId(int id) {
        return unitDao.fetchUnitsByCustomerId(id);
    }

    public Unit bookUnit(BookRequest bookRequest) {
        int priceId = Integer.parseInt(bookRequest.getUnitSize());
        Unit unit = unitDao.fetchOneAvailableUnitByPrice(priceId);
        try {
            makeSubscription(bookRequest, unit.getUnitNumber());
        } catch (StripeException e) {
            log.warn("Error communicating with Stripe server");
            return null;
        }

        unitDao.updateCustomerOfUnit(bookRequest.getCustomerId(), unit.getUnitNumber());
        log.info(String.format("Unit %s booked for customer %s", unit.getUnitNumber(), bookRequest.getCustomerId()));
        return unit;
    }

    public void cancelSubscription(int unitNumber) {
        unitDao.setCustomerToNullForUnit(unitNumber);
    }

    private void makeSubscription(BookRequest bookRequest, int unitNumber) throws StripeException {
        Map<String, Object> params = new HashMap<>();

        Customer customer = customerService.getCustomerById(bookRequest.getCustomerId());
        String customerId = customer.getStripeId();
        params.put("customer", customerId);

        List<Object> items = new ArrayList<>();
        Map<String, Object> price = new HashMap<>();
        Price priceEntity = priceService.getPriceById(Integer.parseInt(bookRequest.getUnitSize()));
        String priceId = priceEntity.getStripeId();
        price.put("price", priceId);

        items.add(price);
        params.put("items", items);

        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(bookRequest.getCardId());
        String paymentId = paymentMethod.getStripeId();
        params.put("default_payment_method", paymentId);

        Date billingAnchor = firstDayOfNextMonth();
        params.put("billing_cycle_anchor", billingAnchor);

        //0 id gets overwritten when added to db
        Transaction transaction = new Transaction(
                0,
                Transaction.Type.BOOK,
                new Date(),
                DateUtil.stringToDate(bookRequest.getStartDate()),
                customer.getId(),
                unitNumber,
                bookRequest.getCardId()
        );

        transactionService.insertTransaction(transaction);

        Subscription.create(params);
    }

    private Date firstDayOfNextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
}
