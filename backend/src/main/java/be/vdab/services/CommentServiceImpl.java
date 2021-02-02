package be.vdab.services;

import be.vdab.domain.Comment;
import be.vdab.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

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
        commentRepository.save(comment);
        return true;
    }

    @Override
    public Iterable<Comment> getCommentsByPostID(Long postID) {
        if(postID == null){
            return new ArrayList<>();
        }
        return commentRepository.findCommentsByPost_Id(postID);

    }
}
