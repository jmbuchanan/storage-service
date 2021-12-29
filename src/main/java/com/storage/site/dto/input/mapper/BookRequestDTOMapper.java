package com.storage.site.dto.input.mapper;

import com.storage.site.domain.BookRequest;
import com.storage.site.dto.input.BookRequestDTO;

public class BookRequestDTOMapper {

    public static BookRequest map(BookRequestDTO bookRequestDTO) {
        return new BookRequest(
                bookRequestDTO.getUnitSize(),
                bookRequestDTO.getStartDate(),
                bookRequestDTO.getCardId()
        );
    }
}
