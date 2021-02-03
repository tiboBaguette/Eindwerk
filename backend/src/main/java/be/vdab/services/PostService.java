package be.vdab.services;

import be.vdab.domain.Post;
import be.vdab.dtos.PostDTO;

public interface PostService {
    boolean createPost(Post post);
    boolean createPost(PostDTO postDTO);
    Iterable<Post> getPosts();
    Post getPostByID(Long postID);

    boolean deletePostByID(Long postID);
    Post editPost(Post post);
}
