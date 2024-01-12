package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.*;
import fun.ruafafa.ityut.annotation.TmspUserApi;

import fun.ruafafa.ityut.interceptor.TmspAddressSource;

@TmspUserApi
@Address(source = TmspAddressSource.class)
public interface TmspAcademicRecordClient {

    /**
     * 获取成绩
     * @return
     */
    @Post(
            url = "/Tschedule/C6Cjgl/GetXskccjResult",
            readTimeout = 10000
    )
    @Body("GET_COURSE_SCORE_REQUEST_DATA")
    String getTotalGrade(String account);
}
