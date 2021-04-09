package com.storage.site.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class CancelRequest {
    @Setter
    private int customerId;
    @Setter
    private String executionDate;
    private final int unitId;
    private final boolean cancelImmediately;
}
