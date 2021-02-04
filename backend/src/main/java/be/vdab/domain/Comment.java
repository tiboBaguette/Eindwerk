package be.vdab.domain;

import be.vdab.dtos.CommentDTO;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Post post;
    private String content;
    @ManyToOne
    private User user;
    private LocalDateTime creationTime;

    public Comment(){
        this.creationTime = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public static class CommentBuilder{
        private Post post;
        private String content;
        private User user;
        private LocalDateTime creationTime;

        public CommentBuilder withPost(Post post){
            this.post = post;
            return this;
        }
        public CommentBuilder withContent(String content){
            this.content = content;
            return this;
        }
        public CommentBuilder withUser(User user){
            this.user = user;
            return this;
        }
        public CommentBuilder withCreationTime(LocalDateTime creationTime){
            this.creationTime = creationTime;
            return this;
        }

        public Comment build(){
            Comment comment = new Comment();
            comment.content = this.content;
            comment.post = this.post;
            comment.user = this.user;
            comment.creationTime = this.creationTime;
            return comment;
        }

    }

}
