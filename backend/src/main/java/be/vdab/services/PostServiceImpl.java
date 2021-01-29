package be.vdab.services;

import be.vdab.domain.Post;
import be.vdab.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public boolean createPost(Post post) {

        return false;
    }
}
