package fun.ruafafa.ityut.server;

import fun.ruafafa.ityut.constant.TyutCampus;
import fun.ruafafa.ityut.dto.GradeReport;
import fun.ruafafa.ityut.dto.StudentInfo;
import fun.ruafafa.ityut.dto.TeachBuilding;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ITyutService {

    /**
     * 登录教务系统
     * @param account
     * @param password
     * @return
     */
    boolean login(String account, String password);

    String getExamSchedule();
    String getAcademicYearTerm();

    String getPjrsjPageListJson() throws Exception;

    StudentInfo getStudentInfo() throws Throwable;


    String getTeachBuildingJson(@NotNull TyutCampus campus);

    List<TeachBuilding> getTeachBuilding(@NotNull TyutCampus campus);

    GradeReport getGradeReport();
}
