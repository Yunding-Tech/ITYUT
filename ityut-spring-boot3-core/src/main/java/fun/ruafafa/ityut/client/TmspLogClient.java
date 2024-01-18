package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.*;
import com.dtflys.forest.http.ForestResponse;
import fun.ruafafa.ityut.dto.LoginUser;
import fun.ruafafa.ityut.interceptor.*;

@Address(source = TmspAddressSource.class)
@Redirection(value = false)
@BaseRequest(interceptor = {ITyutHeaderInterceptor.class, ITyutUserLoginInterceptor.class})
public interface TmspLogClient {

    /**
     * 登录接口
     * @param user
     * @return
     */
    @Post(
            url = "/Login/CheckLogin",
            contentType = "application/x-www-form-urlencoded",
            headers = {"X-Requested-With: XMLHttpRequest"},
            readTimeout = 10000
    )
    ForestResponse<?> login(@Body LoginUser user);

    /**
     * 登出接口
     * @return
     */
    @Get (
            url = "/Login/LoginOut",
            headers = {"X-Requested-With: XMLHttpRequest"},
            readTimeout = 10000
    )
    ForestResponse<?> logout(String account);
}
