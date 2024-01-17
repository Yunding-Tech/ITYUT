package fun.ruafafa.ityut;

import fun.ruafafa.ityut.constant.TyutCampus;
import fun.ruafafa.ityut.dto.GradeReport;
import fun.ruafafa.ityut.dto.StudentInfo;
import fun.ruafafa.ityut.dto.TeachBuilding;
import fun.ruafafa.ityut.manager.ITyutUserManager;
import fun.ruafafa.ityut.server.*;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

@Component
public class ITyutUtil{

    private static ITyutUserManager iTyutUserManager = null;
    private static ITyutService iTyutService = null;
    private static ITyutCourseServer iTyutCourseServer = null;
    private static ITyutExamServer iTyutExamServer = null;
    private static ITyutItergratedService iTyutItergratedService = null;
    private static ITyutStudentServer iTyutStudentServer = null;
    private static ITyutTeachingEvaluationServer iTyutTeachingEvaluationServer = null;

    public static ITyutUtilProxy login(String account, String password) {
        iTyutUserManager.login(account, password);
        return new ITyutUtilProxy(account);
    }

    public static String getClassScheduleBySemester(String account) {
        return iTyutCourseServer.getClassScheduleBySemester(account);
    }

    public static String getAcademicYearTerm(String account) {
        return iTyutExamServer.getExamSchedule(account);
    }

    public static GradeReport getGradeReport(String account) {
        return iTyutStudentServer.getGradeReport(account);
    }

    public static StudentInfo getStudentInfo(String account) {
        return iTyutStudentServer.getStudentInfo(account);
    }


    public static String getTeachingBuildingJSON(String account, TyutCampus campus) {
        return iTyutItergratedService.getTeachBuildingJson(account, campus);
    }

    public static List<TeachBuilding> getTeachingBuilding(String account, TyutCampus campus) {
        return iTyutItergratedService.getTeachBuilding(account, campus);
    }

    public static String onClickEvaluation(String account) {
        return iTyutTeachingEvaluationServer.oneClickEvaluation(account);
    }

    public static String oneClickEvaluation(String account) {
        return iTyutTeachingEvaluationServer.oneClickEvaluation(account);
    }

    public static List<TeachBuilding> getTeachBuilding(String account, TyutCampus campus) {
        return iTyutItergratedService.getTeachBuilding(account, campus);
    }

    public static String getTeachBuildingJson(String account, TyutCampus campus) {
        return iTyutItergratedService.getTeachBuildingJson(account, campus);
    }

    public static String getExamSchedule(String account) {
        return iTyutExamServer.getExamSchedule(account);
    }

    public static void logout(String account) {
        // ...
    }
    @Autowired
    public ITyutUtil(ITyutUserManager iTyutUserManager, ITyutService iTyutService,
                     ITyutCourseServer iTyutCourseServer, ITyutExamServer iTyutExamServer,
                     ITyutStudentServer iTyutStudentServer,
                     ITyutItergratedService iTyutItergratedService,
                     ITyutTeachingEvaluationServer iTyutTeachingEvaluationServer) {
        ITyutUtil.iTyutUserManager = iTyutUserManager;
        ITyutUtil.iTyutService = iTyutService;
        ITyutUtil.iTyutCourseServer = iTyutCourseServer;
        ITyutUtil.iTyutExamServer = iTyutExamServer;
        ITyutUtil.iTyutStudentServer = iTyutStudentServer;
        ITyutUtil.iTyutTeachingEvaluationServer = iTyutTeachingEvaluationServer;
    }



}
