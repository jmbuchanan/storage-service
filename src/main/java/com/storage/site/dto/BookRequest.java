package com.storage.site.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BookRequest {
    private final int unitSize;
    private final String startDate;
    private final int cardId;
}
