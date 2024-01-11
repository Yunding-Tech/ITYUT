package fun.ruafafa.ityut.server.impl;

import fun.ruafafa.ityut.client.TmspCourseClient;
import fun.ruafafa.ityut.constant.RequestConstant;
import fun.ruafafa.ityut.server.ITyutCourseServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ITyutCourseServerImpl implements ITyutCourseServer {

    @Resource
    private TmspCourseClient courseClient;

    @Override
    public String getMajorClassTree(String date) {
        String majorClassTree = courseClient.getMajorClassTree(RequestConstant.DEFUALT_HEADERS, date);
        System.out.println(majorClassTree);
        return null;
    }
    @Override
    public String getClassScheduleBySemester() {
        String courseScheduleHTML = courseClient.getCourseScheduleHTML(RequestConstant.DEFUALT_HEADERS);
        System.out.println(courseScheduleHTML);
        return null;
    }
}
