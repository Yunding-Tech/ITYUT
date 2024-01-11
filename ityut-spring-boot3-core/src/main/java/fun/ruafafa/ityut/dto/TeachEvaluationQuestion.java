package fun.ruafafa.ityut.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class TeachEvaluationQuestion {
    //试题代码
    @JSONField(alternateNames = "id")
    private String questionCode;

    // 答案代码 哈哈(命名真抽象啊)
    @JSONField(alternateNames = "dadm")
    private String answerCode;

    @JSONField(alternateNames = "stlbdm")
    private String questionType;

    @JSONField(alternateNames = "Sjdm")
    private String paperCode;

    @JSONField(alternateNames = "Kch")
    private String courseId;
    @JSONField(alternateNames = "Kxh")
    private String courseNumber;

    @JSONField(alternateNames = "Bpr")
    private String teacherId;

    @JSONField(alternateNames = "name")
    private String questionTitle;

}
