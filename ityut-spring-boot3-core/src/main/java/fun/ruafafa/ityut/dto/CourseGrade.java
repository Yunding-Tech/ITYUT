package fun.ruafafa.ityut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseGrade {

    private String courseCode;
    private String courseName;
    private String englishCourseName;
    private Double credit;
    private String courseType;
    private String examDate;
    private Integer score;
    private String notPassedReason;
}
