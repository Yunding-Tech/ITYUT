package fun.ruafafa.ityut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExamInfo {
    String code;
    String courseName;
    String date;
    String timeLocation;
}
