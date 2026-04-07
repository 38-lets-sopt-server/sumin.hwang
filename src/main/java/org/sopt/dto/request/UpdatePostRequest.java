package org.sopt.dto.request;

public class UpdatePostRequest {
    private final Long id;
    private final String newTitle;
    private final String newContent;

    public UpdatePostRequest(Long id, String newTitle, String newContent) {
        this.id = id;
        this.newTitle = newTitle;
        this.newContent = newContent;
    }

    public Long getId() { return id; }
    public String getNewTitle() { return newTitle; }
    public String getNewContent() { return newContent; }
}
