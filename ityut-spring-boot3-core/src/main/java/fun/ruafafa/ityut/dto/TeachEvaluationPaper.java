package fun.ruafafa.ityut.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class TeachEvaluationPaper {
    private String id;
    @JSONField(alternateNames = "Zxjxjhh")
    private String semester;
    @JSONField(alternateNames = "Sjdm")
    private String paperCode;
    @JSONField(alternateNames = "Sjmc")
    private String paperTitle;
    @JSONField(alternateNames = "Pjr")
    private String studentId;
    @JSONField(alternateNames = "Xm")
    private String studentName;
    @JSONField(alternateNames = "Xsh")
    private String studentNumber;
    @JSONField(alternateNames = "Xsm")
    private String studentCollege;
    @JSONField(alternateNames = "Xym")
    private String studentMajor;
    @JSONField(alternateNames = "Bjh")
    private String studentClass;
    @JSONField(alternateNames = "Kch")
    private String courseId;
    @JSONField(alternateNames = "Kxh")
    private String courseNumber;
    @JSONField(alternateNames = "Kcm")
    private String courseName;
    @JSONField(alternateNames = "Bpr")
    private String teacherId;
    @JSONField(alternateNames = "Jsm")
    private String teacherName;
    @JSONField(alternateNames = "Sfpg")
    private String isEvaluated;
    @JSONField(alternateNames = "R")
    private int row;
    @JSONField(alternateNames = "token")
    private String token;
}
