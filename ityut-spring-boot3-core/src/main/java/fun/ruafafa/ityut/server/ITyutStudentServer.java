package fun.ruafafa.ityut.server;

import fun.ruafafa.ityut.dto.GradeReport;
import fun.ruafafa.ityut.dto.StudentInfo;

public interface ITyutStudentServer {

    StudentInfo getStudentInfo(String account);
    GradeReport getGradeReport(String account);
}
