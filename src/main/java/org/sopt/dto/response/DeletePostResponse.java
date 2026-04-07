package org.sopt.dto.response;

public class DeletePostResponse {
    private final Long id;
    private final String message;

    public DeletePostResponse(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() { return id; }
    public String getMessage() { return message; }
}
