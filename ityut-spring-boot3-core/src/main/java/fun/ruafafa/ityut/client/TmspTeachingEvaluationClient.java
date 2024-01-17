package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.*;
import fun.ruafafa.ityut.dto.PjrsPagination;
import fun.ruafafa.ityut.dto.TeachEvaluationQuestion;
import fun.ruafafa.ityut.interceptor.ITyutCookieInterceptor;
import fun.ruafafa.ityut.interceptor.ITyutHeaderInterceptor;
import fun.ruafafa.ityut.interceptor.ITyutUserInterceptor;
import fun.ruafafa.ityut.interceptor.TmspAddressSource;

import java.util.List;
import java.util.Map;

@Address(source = TmspAddressSource.class)
@BaseRequest(interceptor = {ITyutCookieInterceptor.class, ITyutHeaderInterceptor.class, ITyutUserInterceptor.class})
public interface TmspTeachingEvaluationClient {

    @Post(
            url = "/Tevaluation/CJxpgxs/GetPjrsjPageListJson",
            contentType = "application/x-www-form-urlencoded",
            headers = {"X-Requested-With: XMLHttpRequest"},
            readTimeout = 10000

    )
    @LogEnabled(false)
    String getPjrsjPageListJson(String account, @Body PjrsPagination data);

    @Get(
            url = "/Tevaluation/CJxpgxs/JxpgPjrsjListIndex",
            readTimeout = 10000
    )
    @LogEnabled(false)
    String getPjToken(String account);

    @Post (
            url = "/Tevaluation/CJxpgxs/XssjdaForm",
            readTimeout = 10000,
            contentType = "application/x-www-form-urlencoded",
            headers = {"X-Requested-With: XMLHttpRequest"}
    )
    String submitAnwser(String account, @Body Map<String, String> data, @Body("__RequestVerificationToken") String token);

    @Get (
            url = "/Tevaluation/CJxpgxs/JxpgXssjTreeIndex?kch={kch}&kxh={kxh}&sjdm={sjdm}&bpr={bpr}",
            readTimeout = 10000
    )
    @LogEnabled(false)
    String getPjSubmitToken(String account, @Var("kch") String courseId, @Var("kxh") String courseNumber, @Var("sjdm") String paperCode, @Var("bpr") String teacherId);

    @Post (
            url = "/Tevaluation/CJxpgxs/GetTreeJson",
            readTimeout = 10000,
            contentType = "application/x-www-form-urlencoded"
    )
    @LogEnabled(false)
    List<TeachEvaluationQuestion> getTreeJson(String account, @Body("sjdm") String paperCode);
}
