package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.*;
import fun.ruafafa.ityut.annotation.TmspUserApi;
import fun.ruafafa.ityut.interceptor.CookieInterceptor;
import fun.ruafafa.ityut.interceptor.TmspAddressSource;

import java.util.Map;

@TmspUserApi
public interface TmspCourseClient {

    @Post (
            url = "/Tschedule/Zhcx/GetNjxszyTreeByrwbjJson",
            readTimeout = 10000
    )
    @BodyType("json")
    String getMajorClassTree(@Header Map<String, Object> headers, @Body("semester") String semester);

    @Get (
            url = "/Tresources/A1Xskb/LnXsKbIndex",
            readTimeout = 10000
    )
    String getCourseScheduleHTML(@Header Map<String, Object> headers);
}
