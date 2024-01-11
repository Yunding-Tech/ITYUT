package fun.ruafafa.ityut.dto;

import lombok.Data;

@Data
public class PjrsPagination {
    private Integer limit = 50;
    private Integer offset = 0;
    private String sort = "kch,kxh";
    private String order = "asc";
    private String conditionJson = "{}";
}
