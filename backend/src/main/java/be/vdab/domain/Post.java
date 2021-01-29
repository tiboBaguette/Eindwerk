package be.vdab.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String title;
    private LocalDateTime creationTime;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime){
        this.creationTime = creationTime;
    }

    public User getUser() {
        return user;
    }



    public static class PostBuilder{
        private String content;
        private String title;
        private LocalDateTime creationTime;
        private User user;

        public PostBuilder(){
        }

        public PostBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        public PostBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public PostBuilder withCreationTime(LocalDateTime creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public PostBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public Post build(){
            Post post = new Post();
            post.content = content;
            post.title = title;
            post.creationTime = creationTime;
            post.user = user;
            return post;
        }
    }

}
