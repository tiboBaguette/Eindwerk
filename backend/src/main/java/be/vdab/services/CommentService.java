package be.vdab.services;


import be.vdab.domain.Comment;

public interface CommentService {
    boolean createComment(Comment comment);
}
