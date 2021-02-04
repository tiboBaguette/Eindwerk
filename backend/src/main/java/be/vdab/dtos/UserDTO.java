package be.vdab.dtos;

import be.vdab.domain.User;

public class UserDTO {
    private Long id;
    private String username;

    public UserDTO(){}
    public UserDTO(User user){
        id = user.getId();
        username = user.getUsername();
    }

    public Long getId() {
        return id;
    }

    public UserDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDTO setUsername(String username) {
        this.username = username;
        return this;
    }
}
