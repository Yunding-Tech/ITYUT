package fun.ruafafa.ityut.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class CourseInfo {

    @JSONField(name = "Zxjxjhh")
    private String semester;

    @JSONField(name = "Kch")
    private String courseCode;

    @JSONField(name = "Kxh")
    private String courseNumber;

    @JSONField(name = "Kcm")
    private String courseName;

    @JSONField(name = "Xf")
    private String credit;

    @JSONField(name = "Jsms")
    private String teacherName;

    @JSONField(name = "Zcsm")
    private String week;

    @JSONField(name = "Jc")
    private String section;

    @JSONField(name = "Xqm")
    private String campus;

    @JSONField(name = "Dd")
    private String location;

    @JSONField(name = "Jxlm")
    private String teachingBuilding;

    @JSONField(name = "Jasm")
    private String classroom;
    /**
     * 授课日 （星期几）
     */
    @JSONField(name = "Skxq")
    private String schoolDayOfWeek;
    /**
     * 授课节次 （第几节）
     */
    @JSONField(name = "Skjc")
    private String startSectionNumber;
    /**
     * 出现节长？
     */
    @JSONField(name = "Cxjc")
    private String sectionTime;
    /**
     * 授课周 000000000000000000001
     * <p>长 24 哪里有 1 哪里有课</p>
     */
    @JSONField(name = "Skzc")
    private String schoolWeek;


}
