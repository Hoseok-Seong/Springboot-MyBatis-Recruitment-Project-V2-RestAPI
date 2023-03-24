package shop.mtcoding.job.model.user;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private int id;
    private String username;
    private String password;
    private String salt;
    private String name;
    private String email;
    private String contact;
    private String role;
    private Timestamp createdAt;
}
