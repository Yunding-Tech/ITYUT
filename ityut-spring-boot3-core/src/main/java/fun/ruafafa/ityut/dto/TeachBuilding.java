package fun.ruafafa.ityut.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class TeachBuilding {

    @JSONField(name = "Vaild")
    private String valid;
    @JSONField(name = "Addtime")
    private String addTime;
    /**
     * 优先级 艹真会起名吧！
     */
    @JSONField(name = "Yxj")
    private String priority;
    @JSONField(name = "Id")
    private String id;
    @JSONField(name = "Xqh")
    private String campusNumber;
    @JSONField(name = "Jxlh")
    private String buildingNumber;
    @JSONField(name = "Jxlm")
    private String buildingName;
    /**
     * 备注 （哈哈）
     */
    @JSONField(name = "Bz")
    private String remark;
}
