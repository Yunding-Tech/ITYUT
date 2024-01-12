package fun.ruafafa.ityut.manager.entity;

import com.dtflys.forest.http.ForestCookie;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ITyutUser {
    private String account;
    private String password;
    // Cookie在本地存储的缓存
    private Map<String, List<ForestCookie>> cookieCache;
    private Map<String, Map<String, Integer>> cookieIndexCache;

}
