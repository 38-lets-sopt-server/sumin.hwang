package org.sopt.domain.post.enums;

import org.sopt.common.exception.BusinessException;
import org.sopt.domain.post.code.PostErrorCode;

public enum BoardType {
    FREE, //자유
    HOT, //핫게
    SECRET, //비밀
    ;

    public static BoardType from(String boardType) {
        try {
            return BoardType.valueOf(boardType);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(PostErrorCode.INVALID_BOARD_TYPE);
        }
    }
}
