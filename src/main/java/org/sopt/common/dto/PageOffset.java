package org.sopt.common.dto;

import org.sopt.common.code.GlobalErrorCode;
import org.sopt.common.exception.BusinessException;
import org.springframework.data.domain.PageRequest;

public record PageOffset(
        int page,
        int size
) {

    public static PageOffset of(int page, int size) {
        validate(page, size);

        return new PageOffset(page, size);
    }

    public PageRequest toPageRequest() {
        return PageRequest.of(page, size);
    }

    private static void validate(int page, int size) {
        if (page < 0) {
            throw new BusinessException(GlobalErrorCode.INVALID_PAGE);
        }

        if (size <= 0) {
            throw new BusinessException(GlobalErrorCode.INVALID_PAGE_SIZE);
        }
    }
}
