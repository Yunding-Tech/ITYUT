package fun.ruafafa.ityut.server;

import fun.ruafafa.ityut.dto.CourseInfo;

import java.util.List;
import java.util.Map;

public interface ITyutCourseServer {


    Map<String, String> getSemester(String account);

    List<CourseInfo> getCourseListAtCurrentSemester(String account);

    List<CourseInfo> getCourseInfo(String account, String classNumber);

    List<CourseInfo> getCourseInfo(String account, String semester, String classNumber);
}
