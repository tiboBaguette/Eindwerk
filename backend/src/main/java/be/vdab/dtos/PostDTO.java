package be.vdab.dtos;

import be.vdab.domain.User;
import java.time.LocalDateTime;

public class PostDTO {
    private Long id;
    private String content;
    private String title;
    private LocalDateTime creationTime;
    private User user;
    private String category;

    public Long getId() {
        return id;
    }

    public PostDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PostDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PostDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public PostDTO setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public User getUser() {
        return user;
    }

    public PostDTO setUser(User user) {
        this.user = user;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public PostDTO setCategory(String category) {
        this.category = category;
        return this;
    }
}
