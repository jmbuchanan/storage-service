package com.storage.site.controller;

import com.storage.site.domain.CancelRequest;
import com.storage.site.dto.input.BookRequestDTO;
import com.storage.site.dto.input.CancelRequestDTO;
import com.storage.site.dto.output.SubscriptionDTO;
import com.storage.site.service.SubscriptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@AllArgsConstructor
@Slf4j
public class SubscriptionController {

    private SubscriptionService subscriptionService;

    @PostMapping
    public void create(@RequestBody BookRequestDTO bookRequest, HttpServletRequest request) {
        subscriptionService.create(bookRequest, request);
    }

    @PutMapping
    public void cancel(@RequestBody CancelRequestDTO cancelRequestDTO, HttpServletRequest request) {
        subscriptionService.cancel(cancelRequestDTO, request);
    }

    @GetMapping
    public List<SubscriptionDTO> getActiveSubscriptionsByCustomerId(HttpServletRequest request) {
        return subscriptionService.getActiveSubscriptionsByCustomerId(request);
    }
}
