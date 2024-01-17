package fun.ruafafa.ityut.annotation;

import com.dtflys.forest.annotation.Address;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Redirection;
import fun.ruafafa.ityut.interceptor.ITyutCookieInterceptor;
import fun.ruafafa.ityut.interceptor.ITyutHeaderInterceptor;
import fun.ruafafa.ityut.interceptor.ITyutUserInterceptor;
import fun.ruafafa.ityut.interceptor.TmspAddressSource;

import java.lang.annotation.*;

@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Redirection(value = false)
@Address(source = TmspAddressSource.class)
@BaseRequest(interceptor = {ITyutCookieInterceptor.class, ITyutHeaderInterceptor.class, ITyutUserInterceptor.class})
public @interface TmspUserApi {
}
