package be.vdab.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Post post;
    private String content;

    public long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public String getContent() {
        return content;
    }

    public static class CommentBuilder{
        private Post post;
        private String content;

        public CommentBuilder withPost(Post post){
            this.post = post;
            return this;
        }
        public CommentBuilder withContent(String content){
            this.content = content;
            return this;
        }
        public Comment build(){
            Comment comment = new Comment();
            comment.content = this.content;
            comment.post = this.post;
            return comment;
        }

    }

}
