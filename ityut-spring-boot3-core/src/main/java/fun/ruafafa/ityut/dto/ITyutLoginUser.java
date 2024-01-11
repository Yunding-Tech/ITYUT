package fun.ruafafa.ityut.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ITyutLoginUser {
    private String username;
    private String password;
    private String code = " ";
    private Integer isautologin = 0;
    public Boolean isLogin = false;
}
