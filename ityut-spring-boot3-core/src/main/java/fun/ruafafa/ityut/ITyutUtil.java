package fun.ruafafa.ityut;

import fun.ruafafa.ityut.constant.TyutCampus;
import fun.ruafafa.ityut.dto.*;
import fun.ruafafa.ityut.manager.ITyutUserManager;
import fun.ruafafa.ityut.server.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ITyutUtil{

    private static ITyutUserManager iTyutUserManager = null;
    private static ITyutCourseServer iTyutCourseServer = null;
    private static ITyutExamServer iTyutExamServer = null;
    private static ITyutItergratedService iTyutItergratedService = null;
    private static ITyutStudentServer iTyutStudentServer = null;
    private static ITyutTeachingEvaluationServer iTyutTeachingEvaluationServer = null;

    /**
     * 登录
     * @param account
     * @param password
     * @return
     */
    public static ITyutUtilProxy login(String account, String password) {
        iTyutUserManager.login(account, password);
        return new ITyutUtilProxy(account);
    }

    public static boolean isLogin(String account) {
        return iTyutUserManager.isLogin(account);
    }

    /**
     * 通过学期获取班级课表
     * @param account
     * @return
     */
    public static Map<String, String> getSemester(String account) {
        return iTyutCourseServer.getSemester(account);
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

    public static void oneClickEvaluation(String account) {
        iTyutTeachingEvaluationServer.oneClickEvaluation(account);
    }

    public static List<TeachBuilding> getTeachingBuilding(String account, TyutCampus campus) {
        return iTyutItergratedService.getTeachBuilding(account, campus);
    }

    public static List<ExamInfo> getExamSchedule(String account) {
        return iTyutExamServer.getExamSchedule(account);
    }

    public static List<CourseInfo> getCourseListAtCurrentSemester(String account) {
        return iTyutCourseServer.getCourseListAtCurrentSemester(account);
    }

    public static List<CourseInfo> getCourse(String account, String classNumber) {
        return iTyutCourseServer.getCourseInfo(account, classNumber);
    }

    public static List<CourseInfo> getCourse(String account, String semester, String classNumber) {
        return iTyutCourseServer.getCourseInfo(account, semester, classNumber);
    }

    public static void logout(String account) {
        iTyutUserManager.logout(account);
        return ;
    }
    @Autowired
    public ITyutUtil(ITyutUserManager iTyutUserManager, ITyutCourseServer iTyutCourseServer,
                     ITyutExamServer iTyutExamServer, ITyutStudentServer iTyutStudentServer,
                     ITyutItergratedService iTyutItergratedService, ITyutTeachingEvaluationServer iTyutTeachingEvaluationServer) {
        ITyutUtil.iTyutUserManager = iTyutUserManager;
        ITyutUtil.iTyutCourseServer = iTyutCourseServer;
        ITyutUtil.iTyutExamServer = iTyutExamServer;
        ITyutUtil.iTyutStudentServer = iTyutStudentServer;
        ITyutUtil.iTyutItergratedService = iTyutItergratedService;
        ITyutUtil.iTyutTeachingEvaluationServer = iTyutTeachingEvaluationServer;
    }



}
