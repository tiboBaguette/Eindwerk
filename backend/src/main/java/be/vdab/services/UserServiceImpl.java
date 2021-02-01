package be.vdab.services;

import be.vdab.domain.User;
import be.vdab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean registerUser(User user) {
        if(user == null || user.getUsername() == null || user.getUsername().equals("")){
            return false;
        }
        List<User> foundUser = userRepository.findUserByUsernameOrEmail(user.getUsername(),user.getEmail());
        if(foundUser.isEmpty()){
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User login(User user) {
        return userRepository.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }


}
