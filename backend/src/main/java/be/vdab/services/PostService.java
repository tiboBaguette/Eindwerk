package be.vdab.services;

import be.vdab.domain.Post;

public interface PostService {
    boolean createPost(Post post);
    Iterable<Post> getPosts();
}
