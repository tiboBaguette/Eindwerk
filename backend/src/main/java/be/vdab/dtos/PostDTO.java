package be.vdab.dtos;

import be.vdab.domain.User;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


public class PostDTO {
    public Long id;
    public String content;
    public String title;
    public LocalDateTime creationTime;
    public User user;
    public String category;

}
