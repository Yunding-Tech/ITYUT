package fun.ruafafa.ityutspringboot3;


import fun.ruafafa.ityut.ITyutUtil;
import fun.ruafafa.ityut.ItyutSpringBoot3Application;
import fun.ruafafa.ityut.manager.AutoHostManager;
import fun.ruafafa.ityut.manager.ITyutUserManager;
import fun.ruafafa.ityut.server.ITyutCourseServer;
import fun.ruafafa.ityut.server.ITyutExamServer;
import fun.ruafafa.ityut.server.ITyutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ItyutSpringBoot3Application.class)
class ItyutSpringBoot3ApplicationTests {

    @Autowired
    ITyutService iTyutService;
    @Autowired
    ITyutCourseServer iTyutCourseServer;

    @Autowired
    ITyutExamServer iTyutExamServer;

    @Autowired
    ITyutUserManager iTyutUserManager;

    @Autowired
    AutoHostManager autoHostManager;

    @Test
    void contextLoads() throws Throwable {
        ITyutUtil.login("2022006170", "Ccdy1540");
        System.out.println(ITyutUtil.getGradeReport("2022006170"));
    }

}
