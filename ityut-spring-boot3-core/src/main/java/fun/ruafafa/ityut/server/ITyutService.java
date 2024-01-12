package fun.ruafafa.ityut.server;

import fun.ruafafa.ityut.constant.TyutCampus;
import fun.ruafafa.ityut.dto.GradeReport;
import fun.ruafafa.ityut.dto.StudentInfo;
import fun.ruafafa.ityut.dto.TeachBuilding;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ITyutService {

    String getAcademicYearTerm();

    String getPjrsjPageListJson() throws Exception;


    StudentInfo getStudentInfo(String account) throws Throwable;

}
