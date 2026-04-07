package org.sopt.dto.response;

public class UpdatePostResponse {
    private final Long id;
    private final String message;

    public UpdatePostResponse(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() { return id; }
    public String getMessage() { return message; }
}
