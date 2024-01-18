package fun.ruafafa.ityut.server;

import fun.ruafafa.ityut.dto.CourseGrade;
import fun.ruafafa.ityut.dto.GradeReport;
import fun.ruafafa.ityut.dto.StudentInfo;

import java.util.List;

public interface ITyutStudentServer {

    StudentInfo getStudentInfo(String account);
    GradeReport getGradeReport(String account);

    List<CourseGrade> getCourseGrade(String account);
}
