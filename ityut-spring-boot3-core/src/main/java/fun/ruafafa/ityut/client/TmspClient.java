package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.*;
import com.dtflys.forest.http.ForestResponse;
import fun.ruafafa.ityut.dto.PjrsPagination;
import fun.ruafafa.ityut.dto.TeachEvaluationQuestion;
import fun.ruafafa.ityut.interceptor.CookieInterceptor;
import fun.ruafafa.ityut.interceptor.HeaderInterceptor;
import fun.ruafafa.ityut.dto.ITyutLoginUser;
import fun.ruafafa.ityut.interceptor.TmspAddressSource;

import java.util.List;
import java.util.Map;
@Redirection(value = false)
@Address(source = TmspAddressSource.class)
@BaseRequest(interceptor = {CookieInterceptor.class, HeaderInterceptor.class})
public interface TmspClient {

    /**
     * 测试登录地址是否可用，保持会话
     *
     * @return
     */
    @Get (
            url = "/",
            readTimeout = 10000
    )
    ForestResponse<?> getResponse();

    /**
     * 登录接口
     * @param user
     * @return
     */
    @Post (
            url = "/Login/CheckLogin",
            contentType = "application/x-www-form-urlencoded",
            headers = {"X-Requested-With: XMLHttpRequest"},
            readTimeout = 10000
    )
    Map<String, String> login(@Body ITyutLoginUser user);

    /**
     * 获取考试时间安排
     * @param headers
     * @return
     */
    @Post (
            url = "/Tschedule/C5KwBkks/getExamSchedule",
            contentType = "application/x-www-form-urlencoded",
            readTimeout = 10000
    )
    ForestResponse<String> getExamSchedule(@Body Map<String, Object> data);




    /**
     * 获取学年学期
     * @param headers
     * @return
     */
    @Get (
            url = "/Tschedule/C4Xkgl/XkXsxkIndex",
            readTimeout = 10000
    )
    String getSemester();

    /**
     * 获取成绩
     * @param headers
     * @return
     */
    @Post (
            url = "/Tschedule/C6Cjgl/GetXskccjResult",
            readTimeout = 10000
    )
    @Body("GET_COURSE_SCORE_REQUEST_DATA")
    String getGrade();

    @Post (
            url = "/Tevaluation/CJxpgxs/GetPjrsjPageListJson",
            contentType = "application/x-www-form-urlencoded",
            headers = {"X-Requested-With: XMLHttpRequest"},
            readTimeout = 10000

    )
    String getPjrsjPageListJson(@Body PjrsPagination data);

    @Post (
            url = "/Tevaluation/CJxpgxs/GetTreeJson",
            readTimeout = 10000,
            contentType = "application/x-www-form-urlencoded"
    )
    List<TeachEvaluationQuestion> getTreeJson(@Body("sjdm") String paperCode);
    @Get (
            url = "/Tevaluation/CJxpgxs/JxpgPjrsjListIndex",
            readTimeout = 10000
    )
    String getPjToken();

    @Get (
            url = "/Home/StudentResult",
            readTimeout = 10000
    )
    String getStudentInfo();


    @Post (
            url = "/Tevaluation/CJxpgxs/XssjdaForm",
            readTimeout = 10000,
            contentType = "application/x-www-form-urlencoded"
    )
    String submitAnwser(@Body Map<String, String> data, @Body("__RequestVerificationToken") String token);

    @Get (
            url = "/Tevaluation/CJxpgxs/JxpgXssjTreeIndex?kch={kch}&kxh={kxh}&sjdm={sjdm}&bpr={bpr}",
            readTimeout = 10000
    )
    String getPjSubmitToken(@Var("kch") String courseId, @Var("kxh") String courseNumber, @Var("sjdm") String paperCode, @Var("bpr") String teacherId);

    @Post (
            url = "/Tschedule/Zhcx/GetJxlhByXqh",
            readTimeout = 10000,
            contentType = "application/x-www-form-urlencoded"
    )
    String getTeachBuildingByCampus(@Body("queryJson") String json);

    @Post (
            url = "/Tschedule/Zhcx/GetJxlhByXqh",
            readTimeout = 10000,
            contentType = "application/x-www-form-urlencoded"
    )
    String getTeachBuildings();

}
