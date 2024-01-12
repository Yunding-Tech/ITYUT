package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.*;
import com.dtflys.forest.http.ForestResponse;
import fun.ruafafa.ityut.dto.LoginUser;
import fun.ruafafa.ityut.interceptor.*;

@Redirection(value = false)
@Address(source = TmspAddressSource.class)
@BaseRequest(interceptor = {HeaderInterceptor.class, ITyutUserLoginInterceptor.class})
public interface TmspLoginClient {

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
}
