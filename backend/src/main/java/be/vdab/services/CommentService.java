package be.vdab.services;


import be.vdab.domain.Comment;
import be.vdab.dtos.CommentDTO;

public interface CommentService {
    boolean createComment(Comment comment);
    boolean createComment(CommentDTO commentDTO);
    Iterable<Comment> getCommentsByPostID(Long postID);
}
