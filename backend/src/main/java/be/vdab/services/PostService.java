package be.vdab.services;

import be.vdab.domain.Category;
import be.vdab.domain.Post;
import be.vdab.dtos.PostDTO;

import java.util.List;

public interface PostService {
    boolean createPost(Post post);
    boolean createPost(PostDTO postDTO);
    List<Post> getPosts();
    Post getPostByID(Long postID);
    Iterable<Post> getPostsByCategory(Category category);
    boolean deletePostByID(Long postID);
    Post editPost(Post post);
    Post editPost(PostDTO post);
}
