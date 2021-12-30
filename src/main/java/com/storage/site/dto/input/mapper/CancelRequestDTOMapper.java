package com.storage.site.dto.input.mapper;

import com.storage.site.domain.CancelRequest;
import com.storage.site.dto.input.CancelRequestDTO;

public class CancelRequestDTOMapper {
    public static CancelRequest map(CancelRequestDTO cancelRequestDTO) {
        return CancelRequest.builder()
                .unitId(cancelRequestDTO.getUnitId())
                .date(cancelRequestDTO.getDate())
                .build();
    }
}
