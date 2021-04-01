package com.storage.site.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class CancelRequest {
    private final int unitId;
    private final String executionDate;
}
