package fun.ruafafa.ityut.annotation;

import com.dtflys.forest.annotation.Address;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Redirection;
import fun.ruafafa.ityut.interceptor.CookieInterceptor;
import fun.ruafafa.ityut.interceptor.HeaderInterceptor;
import fun.ruafafa.ityut.interceptor.ITyutUserInterceptor;
import fun.ruafafa.ityut.interceptor.TmspAddressSource;

@Redirection(value = false)
@BaseRequest(interceptor = {CookieInterceptor.class, HeaderInterceptor.class, ITyutUserInterceptor.class})
public @interface TmspUserApi {
}
