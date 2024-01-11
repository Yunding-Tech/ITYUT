package fun.ruafafa.ityut.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class GradeReport {

    @JSONField(alternateNames = "学号：")
    private String studentNum;
    @JSONField(alternateNames = "班级：")
    private String classNum;
    @JSONField(alternateNames = "已修课程学分：")
    private String credit;
    @JSONField(alternateNames = "GPA：")
    private String gpa;
    @JSONField(alternateNames = "GPA专业排名：")
    private String gpaRank;
    @JSONField(alternateNames = "加权班级排名：")
    private String weightedRank;
    @JSONField(alternateNames = "平均成绩：")
    private String averageScore;
    @JSONField(alternateNames = "平均成绩专业排名：")
    private String averageScoreRank;
    @JSONField(alternateNames = "尚不及格学分：")
    private String notPassCredit;
    @JSONField(alternateNames = "姓名：")
    private String name;
    @JSONField(alternateNames = "要求总学分：")
    private String requiredCredit;
    @JSONField(alternateNames = "已修自主实践学分：")
    private String selfPracticeCredit;
    @JSONField(alternateNames = "GPA班级排名：")
    private String gpaClassRank;
    @JSONField(alternateNames = "加权学分成绩：")
    private String weightedScore;
    @JSONField(alternateNames = "加权专业排名：")
    private String weightedMajorRank;
    @JSONField(alternateNames = "平均成绩班级排名：")
    private String averageScoreClassRank;
    @JSONField(alternateNames = "曾不及格学分：")
    private String notPassCreditBefore;

}
