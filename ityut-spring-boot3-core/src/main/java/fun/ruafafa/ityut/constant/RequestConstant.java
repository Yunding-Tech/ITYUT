package fun.ruafafa.ityut.constant;

import java.util.HashMap;
import java.util.Map;

public class RequestConstant {
    public final static Map<String, Object> DEFUALT_HEADERS = new HashMap<>();

    public final static Map<String, Object> PAGINATION = new HashMap<>();

    static {
        DEFUALT_HEADERS.put("Charset", "UTF-8");
        DEFUALT_HEADERS.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0");
        DEFUALT_HEADERS.put("Accept","application/json, text/javascript, */*; q=0.01");;
        DEFUALT_HEADERS.put("Proxy-Connection", "keep-alive");
        // pagination
        PAGINATION.put("pagination[limit]", "15");
        PAGINATION.put("pagination[offset]", "1");
        PAGINATION.put("pagination[sort]", "ksrq");
        PAGINATION.put("pagination[order]", "asc");
        PAGINATION.put("pagination[conditionJson]", "{}");
    }
}
