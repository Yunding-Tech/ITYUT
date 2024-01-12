package fun.ruafafa.ityut.server.impl;

import com.alibaba.fastjson.JSON;
import fun.ruafafa.ityut.client.TmspAcademicRecordClient;
import fun.ruafafa.ityut.dto.GradeReport;
import fun.ruafafa.ityut.server.ITyutAcademicRecordServer;
import jakarta.annotation.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ITyutAcademicRecordServerImpl implements ITyutAcademicRecordServer {

    @Resource
    private TmspAcademicRecordClient tarClient;


    @Override
    public GradeReport getGradeReport(String account) {
        String htmlStr = tarClient.getTotalGrade(account);
        // 使用JSoup解析HTML
        Document document = Jsoup.parse(htmlStr);
        // 从HTML结构中选择学生信息的元素
        Elements profileInfoRows = document.select(".profile-info-row");
        // 遍历每一行学生信息
        HashMap<String, String> map = new HashMap<>();
        for (Element profileInfoRow : profileInfoRows) {
            // 获取信息名称和值
            String infoKey = profileInfoRow.select(".profile-info-name").text().trim();
            String infoValue = profileInfoRow.select(".profile-info-value span").text().trim();
            map.put(infoKey, infoValue);
        }
        String jsonString = JSON.toJSONString(map);
        return JSON.parseObject(jsonString, GradeReport.class);
    }
}
