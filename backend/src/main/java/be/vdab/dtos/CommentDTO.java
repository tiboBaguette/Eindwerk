package be.vdab.dtos;

import be.vdab.domain.Comment;

import java.time.LocalDateTime;

public class CommentDTO {
    private long id;
    private PostDTO post;
    private String content;
    private String user;
    private LocalDateTime creationTime;

    public CommentDTO(){}
    public CommentDTO(Comment comment){
        this.id = comment.getId();
        this.post = new PostDTO(comment.getPost());
        this.content = comment.getContent();
        if(comment.getUser() != null){
            this.user = comment.getUser().getUsername();
        }
        this.creationTime = comment.getCreationTime();
    }

    public long getId() {
        return id;
    }

    public CommentDTO setId(long id) {
        this.id = id;
        return this;
    }

    public PostDTO getPost() {
        return post;
    }

    public CommentDTO setPost(PostDTO post) {
        this.post = post;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public String getUser() {
        return user;
    }

    public CommentDTO setUser(String user) {
        this.user = user;
        return this;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public CommentDTO setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
        return this;
    }
}
