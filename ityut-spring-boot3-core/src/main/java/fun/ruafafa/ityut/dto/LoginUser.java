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
    private String account;

    public LoginUser(String username, String password, String account) {
        this.username = username;
        this.password = password;
        this.account = account;
    }
}
