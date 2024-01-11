package fun.ruafafa.ityutspringboot3;


import fun.ruafafa.ityut.ItyutSpringBoot3Application;
import fun.ruafafa.ityut.server.ITyutCourseServer;
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

    @Test
    void contextLoads() throws Throwable {
        iTyutService.login("2022006170","Ccdy1540");
        String majorClassTree = iTyutCourseServer.getClassScheduleBySemester();
    }

}
