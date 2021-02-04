package be.vdab.services;

import be.vdab.domain.Comment;
import be.vdab.domain.Post;
import be.vdab.domain.User;
import be.vdab.dtos.CommentDTO;
import be.vdab.repositories.CommentRepository;
import be.vdab.repositories.PostRepository;
import be.vdab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean createComment(Comment comment) {
        if(comment == null){
            return false;
        }
        if(comment.getPost() == null || comment.getContent() == null){
            return false;
        }
        if(comment.getContent().equals("")){
            return false;
        }
        //TODO: add a check on user data validity
        commentRepository.save(comment);
        return true;
    }

    @Override
    public boolean createComment(CommentDTO commentDTO){
        if(commentDTO == null){
            return false;
        }
        Post post = null;
        if(commentDTO.getPost() != null){
            post = postRepository.findById(commentDTO.getPost().getId()).orElse(null);
        }
        User user = userRepository.findUserByUsername(commentDTO.getUser());

        Comment comment = new Comment.CommentBuilder()
                .withPost(post)
                .withContent(commentDTO.getContent())
                .withCreationTime(commentDTO.getCreationTime())
                .withUser(user)
                .build();

        return createComment(comment);
    }

    @Override
    public Iterable<Comment> getCommentsByPostID(Long postID) {
        if(postID == null){
            return new ArrayList<>();
        }
        return commentRepository.findCommentsByPost_Id(postID);

    }
}
