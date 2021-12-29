package com.storage.site.controller;

import com.storage.site.domain.PaymentMethod;
import com.storage.site.dto.output.PaymentMethodDTO;
import com.storage.site.service.AuthService;
import com.storage.site.service.PaymentMethodService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/paymentMethods")
@AllArgsConstructor
public class PaymentMethodController {

    private final AuthService authService;
    private final PaymentMethodService paymentMethodService;

    @GetMapping
    public List<PaymentMethodDTO> getPaymentMethodsByCustomerId(HttpServletRequest request, HttpServletResponse response, @RequestParam int customerId) {
        if (customerId == authService.parseCustomerId(request)) {
            return paymentMethodService.getPaymentMethodsByCustomerId(customerId);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ArrayList<>();
        }
    }

    @PostMapping
    public ResponseEntity<String> addPaymentMethod(@RequestBody PaymentMethod paymentMethod, HttpServletRequest request) {
        paymentMethod.setCustomerId(authService.parseCustomerId(request));
        paymentMethodService.addPaymentMethod(paymentMethod);
        return new ResponseEntity<>("Payment method successfully added", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaymentMethodById(@PathVariable int id, HttpServletRequest request) {
        int customerId = authService.parseCustomerId(request);
        paymentMethodService.setInactive(id, customerId);
        return new ResponseEntity<>("Resource deleted", HttpStatus.OK);
    }
}
