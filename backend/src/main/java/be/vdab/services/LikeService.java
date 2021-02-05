package be.vdab.services;

import be.vdab.domain.Like;

public interface LikeService {
    Like addLike(Long postID, String username);
    boolean removeLike(Long postID, String username);
    Integer getLikes(Long postID);
}
