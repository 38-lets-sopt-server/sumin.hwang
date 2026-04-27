package org.sopt.common.dto;

import org.springframework.util.Assert;

public record PageOffset(
        int page,
        int size
) {

    public static PageOffset of(int page, int size) {
        Assert.isTrue(page >= 0, "page는 0 이상이어야 합니다.");
        Assert.isTrue(size > 0, "size는 양수여야 합니다.");

        return new PageOffset(page, size);
    }
}
