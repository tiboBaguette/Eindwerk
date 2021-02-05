package be.vdab.services;

import be.vdab.domain.Like;
import be.vdab.repositories.LikeRepository;
import be.vdab.repositories.PostRepository;
import be.vdab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;


    @Override
    public Like addLike(Long postID, String username) {
        if(postID == null || username == null){
            return null;
        }
        Like newLike = new Like(
                postRepository.findById(postID).orElse(null),
                userRepository.findUserByUsername(username)
        );
        if(newLike.getPost() == null || newLike.getUser() == null){
            return null;
        }
        if(likeRepository.findLikeByPost_IdAndUser_Username(postID,username) != null){
            return null;
        }
        return likeRepository.save(newLike);
    }

    @Override
    public boolean removeLike(Long postID, String username) {
        // TODO: cannot remove -> see LikeRepository
        if(postID == null || username == null){
            return false;
        }
        Like foundLike = likeRepository.findLikeByPost_IdAndUser_Username(postID,username);
        if( foundLike == null){
            return false;
        }
        likeRepository.delete(foundLike);
        return true;
    }

    @Override
    public Integer getLikes(Long postID) {
        if(postID == null){
            return null;
        }
        if(postRepository.findById(postID).orElse(null) == null){
            return null;
        }
        return likeRepository.countLikesByPost_Id(postID);
    }
}
