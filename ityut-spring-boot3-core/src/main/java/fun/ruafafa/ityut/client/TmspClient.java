package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.*;
import fun.ruafafa.ityut.interceptor.*;

@Address(source = TmspAddressSource.class)
@BaseRequest(interceptor = {ITyutCookieInterceptor.class, ITyutHeaderInterceptor.class, ITyutUserInterceptor.class})
public interface TmspClient {


    /**
     * 获取学年学期
     * @return
     */
    @Get (
            url = "/Tschedule/C4Xkgl/XkXsxkIndex",
            readTimeout = 10000
    )
    String getSemester();



}
