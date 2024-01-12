package fun.ruafafa.ityut;

import fun.ruafafa.ityut.constant.TyutCampus;
import fun.ruafafa.ityut.dto.GradeReport;
import fun.ruafafa.ityut.dto.TeachBuilding;
import fun.ruafafa.ityut.manager.ITyutUserManager;
import fun.ruafafa.ityut.server.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ITyutUtil{

    private static ITyutUserManager iTyutUserManager = null;
    private static ITyutService iTyutService = null;
    private static ITyutCourseServer iTyutCourseServer = null;
    private static ITyutExamServer iTyutExamServer = null;
    private static ITyutItergratedService iTyutItergratedService = null;
    private static ITyutAcademicRecordServer iTyutAcademicRecordServer = null;


    public static void login(String account, String password) {
        iTyutUserManager.login(account, password);
    }

    public static GradeReport getGradeReport(String account) {
        return iTyutAcademicRecordServer.getGradeReport(account);
    }

    public static String getTeachingBuildingJSON(String account, TyutCampus campus) {
        return iTyutItergratedService.getTeachBuildingJson(account, campus);
    }

    public static List<TeachBuilding> getTeachingBuilding(String account, TyutCampus campus) {
        return iTyutItergratedService.getTeachBuilding(account, campus);
    }


    public static void logout(String account) {
        // ...
    }


    @Autowired
    public ITyutUtil(ITyutUserManager iTyutUserManager, ITyutService iTyutService,
                     ITyutCourseServer iTyutCourseServer, ITyutExamServer iTyutExamServer,
                     ITyutAcademicRecordServer iTyutAcademicRecordServer,
                     ITyutItergratedService iTyutItergratedService) {
        ITyutUtil.iTyutUserManager = iTyutUserManager;
        ITyutUtil.iTyutService = iTyutService;
        ITyutUtil.iTyutCourseServer = iTyutCourseServer;
        ITyutUtil.iTyutExamServer = iTyutExamServer;
        ITyutUtil.iTyutAcademicRecordServer = iTyutAcademicRecordServer;
        ITyutUtil.iTyutItergratedService = iTyutItergratedService;
    }
}
