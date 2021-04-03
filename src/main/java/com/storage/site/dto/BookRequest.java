package com.storage.site.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class BookRequest {
    private final String unitSize;
    private final String startDate;
    private final int cardId;
}
