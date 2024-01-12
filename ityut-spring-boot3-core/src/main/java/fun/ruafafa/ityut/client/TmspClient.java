package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.*;
import fun.ruafafa.ityut.dto.PjrsPagination;
import fun.ruafafa.ityut.dto.TeachEvaluationQuestion;
import fun.ruafafa.ityut.interceptor.*;

import java.util.List;
import java.util.Map;
@Redirection(value = false)
@Address(source = TmspAddressSource.class)
@BaseRequest(interceptor = {CookieInterceptor.class, HeaderInterceptor.class, ITyutUserInterceptor.class})
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
    String getStudentInfo(String account);


    @Post (
            url = "/Tevaluation/CJxpgxs/XssjdaForm",
            readTimeout = 10000,
            contentType = "application/x-www-form-urlencoded",
            headers = {"X-Requested-With: XMLHttpRequest"}
    )
    String submitAnwser(@Body Map<String, String> data, @Body("__RequestVerificationToken") String token);

    @Get (
            url = "/Tevaluation/CJxpgxs/JxpgXssjTreeIndex?kch={kch}&kxh={kxh}&sjdm={sjdm}&bpr={bpr}",
            readTimeout = 10000
    )
    String getPjSubmitToken(@Var("kch") String courseId, @Var("kxh") String courseNumber, @Var("sjdm") String paperCode, @Var("bpr") String teacherId);


}
