package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.*;
import fun.ruafafa.ityut.interceptor.ITyutCookieInterceptor;
import fun.ruafafa.ityut.interceptor.ITyutHeaderInterceptor;
import fun.ruafafa.ityut.interceptor.ITyutUserInterceptor;
import fun.ruafafa.ityut.interceptor.TmspAddressSource;

@Redirection(value = false)
@Address(source = TmspAddressSource.class)
@BaseRequest(interceptor = {ITyutCookieInterceptor.class, ITyutHeaderInterceptor.class, ITyutUserInterceptor.class})
public interface TmspStudentServerClient {

    /**
     * 获取GPA等成绩
     * @return
     */
    @Post (
            url = "/Tschedule/C6Cjgl/GetXskccjResult",
            readTimeout = 10000
    )
    @Body("GET_COURSE_SCORE_REQUEST_DATA")
    String getTotalGradeHTML(String account);


    @Post (
            url = "/Tschedule/C6Cjgl/GetKccjResult",
            contentType = "application/x-www-form-urlencoded",
            headers = {"X-Requested-With: XMLHttpRequest"},
            data="order=zxjxjhh desc,kch",
            readTimeout = 10000
    )
    String getHistoryGradeHTML(String account);

    /**
     * 获取学生信息
     * @param account
     * @return
     */
    @Get (
            url = "/Home/StudentResult",
            readTimeout = 10000
    )
    String getStudentInfoHTML(String account);
}
