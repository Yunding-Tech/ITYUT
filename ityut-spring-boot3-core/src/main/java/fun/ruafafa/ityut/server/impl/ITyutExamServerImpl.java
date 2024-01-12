package fun.ruafafa.ityut.server.impl;

import com.dtflys.forest.http.ForestResponse;
import fun.ruafafa.ityut.client.TmspExamClient;
import fun.ruafafa.ityut.constant.RequestConstant;
import fun.ruafafa.ityut.server.ITyutExamServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ITyutExamServerImpl implements ITyutExamServer {
    @Resource
    private TmspExamClient tmspExamClient;
    /**
     * 获取考试安排
     * @return
     */
    @Override
    public String getExamSchedule(String account) {
        ForestResponse<String> response = tmspExamClient.getExamSchedule(account, RequestConstant.PAGINATION);
        System.out.println(response.getContent());
        return null;
    }
}
