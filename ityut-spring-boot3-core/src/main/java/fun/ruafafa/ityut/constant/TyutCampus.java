package fun.ruafafa.ityut.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TyutCampus {
    /**
     * 所有校区
     */
    ALL("00"),
    /**
     * 迎西校区
     */
    YING_XI("01"),
    /**
     * 虎裕校区
     */
    HU_YU("02"),
    /**
     * 未知校区
     */
    UNKNOWN_03("03"),
    /**
     * 未知校区
     */
    UNKNOWN_04("04"),
    /**
     * 未知校区
     */
    UNKNOWN_05("05"),
    /**
     * 实验校区
     */
    SHI_YAN("06"),
    /**
     * 未知校区
     */
    UNKNOWN_07("07"),
    /**
     * 明向校区
     */
    MING_XIANG("08"),
    /**
     * 校外
     */
    XIAO_WAI("09"),
    /**
     * 线上
     */
    XIAN_SHANG("10"),




    ;




    private String id;



}
