package fun.ruafafa.ityut.server.impl;

import com.dtflys.forest.http.ForestResponse;
import fun.ruafafa.ityut.client.TmspExamClient;
import fun.ruafafa.ityut.constant.RequestConstant;
import fun.ruafafa.ityut.dto.ExamInfo;
import fun.ruafafa.ityut.server.ITyutExamServer;
import jakarta.annotation.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ITyutExamServerImpl implements ITyutExamServer {
    @Resource
    private TmspExamClient tmspExamClient;
    /**
     * 获取考试安排
     *
     * @return
     */
    @Override
    public List<ExamInfo> getExamSchedule(String account) {
        ForestResponse<String> response = tmspExamClient.getExamSchedule(account, RequestConstant.PAGINATION_ARRAY);
        String html = response.getContent();
        List<ExamInfo> examList = new ArrayList<>();

        Document document = Jsoup.parse(html);
        Element table = document.select("table").first();
        Elements rows = table.select("tr");

        for (int i = 1; i < rows.size(); i++) { // 跳过表头行
            Element row = rows.get(i);
            Elements columns = row.select("td");

            String code = columns.get(0).text();
            String courseName = columns.get(1).text();
            String date = columns.get(2).select("font").text();
            String timeLocation = columns.get(3).text();

            ExamInfo examInfo = new ExamInfo(code, courseName, date, timeLocation);
            examList.add(examInfo);
        }

        return examList;
    }
}
