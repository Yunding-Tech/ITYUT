package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.*;
import fun.ruafafa.ityut.interceptor.ITyutCookieInterceptor;
import fun.ruafafa.ityut.interceptor.ITyutHeaderInterceptor;
import fun.ruafafa.ityut.interceptor.ITyutUserInterceptor;
import fun.ruafafa.ityut.interceptor.TmspAddressSource;

@Address(source = TmspAddressSource.class)
@BaseRequest(interceptor = {ITyutCookieInterceptor.class, ITyutHeaderInterceptor.class, ITyutUserInterceptor.class})
public interface TmspItergratedClient {

    @Post(
            url = "/Tschedule/Zhcx/GetJxlhByXqh",
            readTimeout = 10000,
            contentType = "application/x-www-form-urlencoded"
    )
    String getTeachBuildingByCampus(String account, @Body("queryJson") String json);

    @Post (
            url = "/Tschedule/Zhcx/GetJxlhByXqh",
            readTimeout = 10000,
            contentType = "application/x-www-form-urlencoded"
    )
    String getTeachBuildings(String account);

    @Post (
            url = "/Tschedule/Zhcx/GetNjxszyTreeByrwbjJson",
            readTimeout = 10000
    )
    @BodyType("json")
    String getMajorClassTree(String account, @Body("zxjxjhh") String semester);

}
