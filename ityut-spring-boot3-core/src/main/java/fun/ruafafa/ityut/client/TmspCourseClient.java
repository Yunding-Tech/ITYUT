package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.*;
import fun.ruafafa.ityut.interceptor.ITyutCookieInterceptor;
import fun.ruafafa.ityut.interceptor.ITyutHeaderInterceptor;
import fun.ruafafa.ityut.interceptor.ITyutUserInterceptor;
import fun.ruafafa.ityut.interceptor.TmspAddressSource;

import java.util.Map;

@Address(source = TmspAddressSource.class)
@BaseRequest(interceptor = {ITyutCookieInterceptor.class, ITyutHeaderInterceptor.class, ITyutUserInterceptor.class})
public interface TmspCourseClient {

    @Post (
            url = "/Tschedule/Zhcx/GetNjxszyTreeByrwbjJson",
            readTimeout = 10000
    )
    @BodyType("json")
    String getMajorClassTree(String account, @Body("semester") String semester);

    @Get (
            url = "/Tresources/A1Xskb/LnXsKbIndex",
            readTimeout = 10000
    )
    String getCourseScheduleHTML(String account);

    @Post (
            url = "/BaseManager/TbBjb/GetListJson",
            readTimeout = 10000
    )
    String serachMajorClass(String account, @Body("queryJson") String json);

    @Post (
            url = "/Tresources/A1Xskb/GetXsKb",
            readTimeout = 10000
    )
    String getCourseSchedule(String account);

    @Post (
            url = "/Tschedule/Zhcx/GetSjjsSjddByBjh",
            charset = "utf-8",
            contentType = "application/x-www-form-urlencoded",
            headers = {"X-Requested-With: XMLHttpRequest"},
            readTimeout = 10000
    )
    String getCouresSchedule(String account, @Body Map<String, Object> data);

    @Post (
            url = "/Tresources/A1Xskb/GetPageListJson2",
            charset = "utf-8",
            contentType = "application/x-www-form-urlencoded",
            headers = {"X-Requested-With: XMLHttpRequest"},
            readTimeout = 10000
    )
    String getCouresDetails(String account, @Body Map<String, Object> data);


    @Post (
            url = "/Tschedule/C4Xkgl/GetXkPageListJson",
            charset = "utf-8",
            contentType = "application/x-www-form-urlencoded",
            headers = {"X-Requested-With: XMLHttpRequest"},
            readTimeout = 10000
    )
    String getSelectCourseList(String account, @Body Map<String, Object> data);
}
