package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.*;
import com.dtflys.forest.http.ForestResponse;
import fun.ruafafa.ityut.annotation.TmspUserApi;


import java.util.Map;

@TmspUserApi
public interface TmspExamClient {

    /**
     * 获取考试时间安排
     * @return
     */
    @Post(
            url = "/Tschedule/C5KwBkks/GetKsxxByDesk",
            contentType = "application/x-www-form-urlencoded",
            headers = {"X-Requested-With: XMLHttpRequest"},
            readTimeout = 10000
    )
    ForestResponse<String> getExamSchedule(String account, @Body Map<String, Object> data);
}
