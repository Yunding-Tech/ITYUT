package fun.ruafafa.ityut.dto;

import lombok.Data;

@Data
public class CourseGrade {

    private String courseCode;
    private String courseName;
    private String englishCourseName;
    private Integer credit;
    private String courseType;
    private String examDate;
    private Integer score;
    private String notPassedReason;
}
