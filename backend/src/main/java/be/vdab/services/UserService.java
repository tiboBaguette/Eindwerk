package be.vdab.services;

import be.vdab.domain.User;


public interface UserService {
    User saveUser(User user);
    boolean registerUser(User user);

    User login(User user1);
}
