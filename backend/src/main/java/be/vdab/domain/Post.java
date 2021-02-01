package be.vdab.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

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

    public Category getCategory() {
        return category;
    }

    public static class PostBuilder{
        private String content;
        private String title;
        private LocalDateTime creationTime;
        private User user;
        private Category category;

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

        public PostBuilder withCategory(Category category){
            this.category = category;
            return this;
        }

        public Post build(){
            Post post = new Post();
            post.content = content;
            post.title = title;
            post.creationTime = creationTime;
            post.user = user;
            post.category = category;
            return post;
        }
    }

}
