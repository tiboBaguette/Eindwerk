package be.vdab.services;

import be.vdab.domain.User;


public interface UserService {
    public User saveUser(User user);

    public void doNothing();
}
