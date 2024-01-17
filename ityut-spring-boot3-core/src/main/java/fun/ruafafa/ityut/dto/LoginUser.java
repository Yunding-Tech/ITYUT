package fun.ruafafa.ityut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class LoginUser {
    private String username;
    private String password;
    private String code = " ";
    private Integer isautologin = 0;

    public LoginUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
