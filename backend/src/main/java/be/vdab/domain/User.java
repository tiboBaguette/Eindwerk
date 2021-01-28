package be.vdab.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "applicationUser")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    private String password;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @NoArgsConstructor
    public static class UserBuilder{
        long id;
        String username;
        String email;
        String password;

        public UserBuilder withId(long id){
            this.id = id;
            return this;
        }
        public UserBuilder withUsername(String username){
            this.username = username;
            return this;
        }
        public UserBuilder withEmail(String email){
            this.email = email;
            return this;
        }
        public UserBuilder withPassword(String password){
            this.password = password;
            return this;
        }
        public User build(){
            User user = new User();
            user.username = this.username;
            user.email = this.email;
            user.password = this.password;
            return user;
        }

    }

}
