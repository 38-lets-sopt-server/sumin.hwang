package org.sopt.common.dto;

import java.util.List;
import org.springframework.data.domain.Page;

public record PageResult<T>(
        List<T> contents,
        long totalCount,
        long totalPages
) {

    public static <T> PageResult<T> from(Page<T> page) {
        return new PageResult<>(page.toList(), page.getTotalElements(), page.getTotalPages());
    }
}
