package fun.ruafafa.ityut.server.impl;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.dtflys.forest.annotation.NotNull;
import fun.ruafafa.ityut.annotation.TmspConvert;
import fun.ruafafa.ityut.client.TmspStudentServerClient;
import fun.ruafafa.ityut.dto.CourseGrade;
import fun.ruafafa.ityut.dto.GradeReport;
import fun.ruafafa.ityut.dto.StudentInfo;
import fun.ruafafa.ityut.server.ITyutStudentServer;
import jakarta.annotation.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ITyutStudentServerImpl implements ITyutStudentServer {

    @Resource
    private TmspStudentServerClient tarClient;

    @Override
    public GradeReport getGradeReport(String account) {
        String htmlStr = tarClient.getTotalGradeHTML(account);
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

    @Override
    public StudentInfo getStudentInfo(String account) {
        String htmlStr = tarClient.getStudentInfoHTML(account);
        StudentInfo studentInfo = new StudentInfo();
        // TODO: info?
        return grabHtmlInfoToBean(htmlStr, studentInfo, ".profile-info-row:contains({}) .profile-info-value span");
    }


    /**
     * 从HTML中提取信息并封装到Bean中
     * @apiNote bean 必须有符合规范的 setter 方法
     * @param htmlStr
     * @param bean
     * @return
     * @param <T>
     * @throws Throwable
     */
    @NotNull
    private  static <T> T grabHtmlInfoToBean (String htmlStr, T bean, String template) {
        // 使用JSoup解析HTML
        Document doc = Jsoup.parse(htmlStr);
        // 使用 MethodHandles 获取 MethodHandle
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType setterMethod = MethodType.methodType(void.class, String.class);
        // 使用传统反射获取对象的所有属性并处理
        Field[] fields = StudentInfo.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String methodName = StrUtil.toCamelCase("set" + StrUtil.upperFirst(field.getName()));
            TmspConvert annotation = field.getAnnotation(TmspConvert.class);
            Element element;
            if (annotation != null) {
                String value = annotation.value();
                element = doc.select(StrUtil.format(template, value)).first();
            } else {
                element = doc.select(template).first();
            }
            MethodHandle setterHandle = null;
            try {
                setterHandle = lookup.findVirtual(bean.getClass(), methodName, setterMethod);
                if (element != null) setterHandle.invoke(bean, element.text());
            } catch (Throwable e) {
                e.printStackTrace();
                throw new RuntimeException("获取属性异常");
            }
        }
        return bean;
    }
    @Override
    public List<CourseGrade> getCourseGrade(String account) {
        String html = tarClient.getHistoryGradeHTML(account);

        List<CourseGrade> courseGrades = new ArrayList<>();

        Document document = Jsoup.parse(html);

        Elements rows = document.select("table tr:has(td)");

        for (Element row : rows) {
            Elements columns = row.select("td");
            if (columns.size() == 9) {  // 确保列数正确
                String courseCode = columns.get(0).text();
                String courseName = columns.get(2).text();
                String englishCourseName = columns.get(3).text();
                double credit = Double.parseDouble(columns.get(4).text());
                String courseAttribute = columns.get(5).text();
                String examTime = columns.get(6).text();
                int score = Integer.parseInt(columns.get(7).text());
                String notPassedReason = columns.get(8).text();

                CourseGrade courseGrade = new CourseGrade(courseCode, courseName, englishCourseName,
                        credit, courseAttribute, examTime, score, notPassedReason);

                courseGrades.add(courseGrade);
            }
        }

        return courseGrades;
    }
}
