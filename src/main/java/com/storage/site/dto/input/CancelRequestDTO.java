package com.storage.site.dto.input;

import lombok.Data;

@Data
public class CancelRequestDTO {
    private final int unitId;
    private final String date;
}
