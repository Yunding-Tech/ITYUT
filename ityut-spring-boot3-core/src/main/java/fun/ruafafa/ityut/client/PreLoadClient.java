package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Redirection;
import com.dtflys.forest.annotation.Var;
import com.dtflys.forest.http.ForestResponse;
import fun.ruafafa.ityut.interceptor.CookieInterceptor;
import org.springframework.stereotype.Component;

@Redirection(value = false)
@BaseRequest(interceptor = CookieInterceptor.class)
public interface PreLoadClient {

    @Get(
            url = "http://{address}/",
            readTimeout = 10000
    )
    ForestResponse<?> getResponse(@Var("address") String address);
}
