package fun.ruafafa.ityut.dto;

import fun.ruafafa.ityut.annotation.TmspConvert;
import lombok.Data;

@Data
public class StudentInfo {
    /**
     * 用户名、姓名、性别、校区、最后登录时间、学号、家庭户籍地、入学日期、院系、学制类型、国家学籍、是否应届毕业生、身份证号、政治面貌、年纪、专业、班级、校内学籍
     */
    @TmspConvert("用户名")
    private String username;
    @TmspConvert("姓名")
    private String name;
    @TmspConvert("性别")
    private String gender;
    @TmspConvert("校区")
    private String campus;
    @TmspConvert("最后登录时间")
    private String lastLoginTime;
    @TmspConvert("学号")
    private String studentId;
    @TmspConvert("家庭户籍地")
    private String familyAddress;
    @TmspConvert("入学日期")
    private String admissionDate;
    @TmspConvert("院系")
    private String department;
    @TmspConvert("学制类型")
    private String studentType;
    @TmspConvert("国家学籍")
    private String nationality;
    @TmspConvert("是否应届毕业生")
    private String isGraduate;
    @TmspConvert("身份证号")
    private String idCard;
    @TmspConvert("政治面貌")
    private String politicalStatus;
    @TmspConvert("年级")
    private String grade;
    @TmspConvert("专业")
    private String major;
    @TmspConvert("班级")
    private String classNum;
    @TmspConvert("校内学籍")
    private String schoolRoll;
}
