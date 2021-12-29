package com.storage.site.dto.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BookRequestDTO {
    private final int unitSize;
    private final String startDate;
    private final int cardId;
}
