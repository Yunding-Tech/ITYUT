package fun.ruafafa.ityut.server.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fun.ruafafa.ityut.client.TmspCourseClient;
import fun.ruafafa.ityut.constant.RequestConstant;
import fun.ruafafa.ityut.dto.CourseInfo;
import fun.ruafafa.ityut.server.ITyutCourseServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 课程与考试
 * @author Ruafafa
 */
@Component
public class ITyutCourseServerImpl implements ITyutCourseServer {

    @Resource
    private TmspCourseClient courseClient;

    @Override
    public String getClassScheduleBySemester(String account) {
        String courseScheduleHTML = courseClient.getCourseScheduleHTML(account);
        System.out.println(courseScheduleHTML);
        return null;
    }
    @Override
    public List<CourseInfo> getCourseListAtCurrentSemester(String account) {
        String json = courseClient.getCourseSchedule(account);
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray rows = jsonObject.getJSONArray("rows");
        return rows.toJavaList(CourseInfo.class);
    }
    @Override
    public List<CourseInfo> getCourseInfoByClassNumber(String account, String classNumber) {
        Map<String, Object> paginationArr = new HashMap<>(RequestConstant.PAGINATION_ARRAY);
        Map<String, Object> pagination = new HashMap<>(RequestConstant.PAGINATION);

        setParam(classNumber, paginationArr, pagination);

        String jsonSchedule = courseClient.getCouresScheduleByClassNumber(account, paginationArr);
        String jsonDetails = courseClient.getCouresDetailsByClassNumber(account, pagination);
        List<CourseInfo> schedules = getList(jsonSchedule);
        List<CourseInfo> details = getList(jsonDetails);

        return mergeCourseInfo(details, schedules);
    }

    private static List<CourseInfo> mergeCourseInfo(List<CourseInfo> details, List<CourseInfo> schedules) {
        Map<String, CourseInfo> map = details.stream()
                .collect(Collectors.toMap(i -> courseUID(i), i -> i));

        List<CourseInfo> result = schedules.stream()
                .map(iMain -> {
                    CourseInfo i = map.get(courseUID(iMain));
                    if (i != null) {
                        // 如果user2存在，就将user1和user2的属性值互补，这里可以根据实际情况判断哪个属性值优先
                        copyPerperties(iMain, i);
                    }
                    // 返回user1作为结果集合的元素
                    return iMain;
                })
                .collect(Collectors.toList());

        result.addAll(map.values().stream()
                // 过滤掉在list1中已经存在的User对象
                .filter(i -> details.stream().noneMatch(iMain -> courseUID(iMain).equals(courseUID(i))))
                // 将Stream转换成List
                .toList());

        return result;
    }

    private static void copyPerperties(CourseInfo iMain, CourseInfo i) {
        iMain.setCredit(i.getCredit());
        iMain.setTeacherName(i.getTeacherName());
        iMain.setSection(i.getSection());
        iMain.setTeachingBuilding(i.getTeachingBuilding());
        iMain.setClassroom(i.getClassroom());
        iMain.setSchoolDayOfWeek(i.getSchoolDayOfWeek());
        iMain.setCampus(i.getCampus());
    }

    private static String courseUID(CourseInfo i) {
        return StrUtil.format("{}-{}-{}-{}",
                i.getCourseCode(), i.getCourseNumber(), i.getSchoolDayOfWeek(),
                i.getWeek());
    }

    private static void setParam(String classNumber, Map<String, Object> paginationArr, Map<String, Object> pagination) {
        paginationArr.put("pagination[conditionJson]", "{bjh:'" + classNumber + "'}");
        paginationArr.put("pagination[sort]", "kch,kxh");
        pagination.put("conditionJson", "{bjh:'" + classNumber + "'}");
        pagination.put("sort", "kch,kxh");
    }

    private static List<CourseInfo> getList(String json) {
        JSONObject josonObj = JSON.parseObject(json);
        JSONArray rows = josonObj.getJSONArray("rows");
        return rows.toJavaList(CourseInfo.class);
    }
}
