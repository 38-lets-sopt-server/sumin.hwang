package org.sopt.enums;

public enum ErrorMessage {
    POST_NOT_FOUND("게시글을 찾을 수 없습니다.");

    private final String clientMessage;

    ErrorMessage(String clientMessage) {
        this.clientMessage = clientMessage;
    }

    public String getClientMessage() {
        return clientMessage;
    }
}
