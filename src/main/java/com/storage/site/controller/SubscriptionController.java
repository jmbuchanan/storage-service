package com.storage.site.controller;

import com.storage.site.dto.BookRequest;
import com.storage.site.service.SubscriptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/subscriptions")
@AllArgsConstructor
@Slf4j
public class SubscriptionController {

    private SubscriptionService subscriptionService;

    @PostMapping
    public void book(@RequestBody BookRequest bookRequest, HttpServletRequest request) {
        subscriptionService.create(bookRequest, request);
    }
}
