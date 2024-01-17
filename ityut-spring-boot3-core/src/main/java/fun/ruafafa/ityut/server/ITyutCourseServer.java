package fun.ruafafa.ityut.server;

import fun.ruafafa.ityut.dto.CourseInfo;

import java.util.List;

public interface ITyutCourseServer {


    String getClassScheduleBySemester(String account);

    List<CourseInfo> getCourseListAtCurrentSemester(String account);

    List<CourseInfo> getCourseInfoByClassNumber(String account, String classNumber);
}
