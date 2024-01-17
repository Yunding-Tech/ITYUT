package fun.ruafafa.ityut;

import fun.ruafafa.ityut.constant.TyutCampus;
import fun.ruafafa.ityut.dto.GradeReport;
import fun.ruafafa.ityut.dto.StudentInfo;
import fun.ruafafa.ityut.dto.TeachBuilding;
import java.util.List;

public class ITyutUtilProxy {

    private String account;

    public ITyutUtilProxy(String account) {
        this.account = account;
    }

    public String getClassScheduleBySemester() {
        return ITyutUtil.getClassScheduleBySemester(account);
    }

    public String getAcademicYearTerm() {
        return ITyutUtil.getExamSchedule(account);
    }

    public GradeReport getGradeReport() {
        return ITyutUtil.getGradeReport(account);
    }

    public StudentInfo getStudentInfo() {
        return ITyutUtil.getStudentInfo(account);
    }


    public String getTeachingBuildingJSON(TyutCampus campus) {
        return ITyutUtil.getTeachBuildingJson(account, campus);
    }

    public List<TeachBuilding> getTeachingBuilding(TyutCampus campus) {
        return ITyutUtil.getTeachBuilding(account,campus);
    }

    public String onClickEvaluation() {
        return ITyutUtil.oneClickEvaluation(account);
    }

    public static void logout(String account) {
        // ...
    }
}
