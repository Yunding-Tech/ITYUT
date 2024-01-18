package fun.ruafafa.ityut.manager;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.dtflys.forest.http.ForestCookie;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import fun.ruafafa.ityut.client.TmspLogClient;
import fun.ruafafa.ityut.constant.LoginConstant;
import fun.ruafafa.ityut.dto.LoginUser;
import fun.ruafafa.ityut.manager.entity.ITyutUser;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
@Component
public class ITyutUserManager {
    @Resource
    private TmspLogClient tmspLogClient;

    private final Map<String, ITyutUser> userMap = new ConcurrentHashMap<>();

    public void login(String account, String password) {
        // 使用 公钥 RSA 加密 表单数据
        RSA rsa = SecureUtil.rsa(null, LoginConstant.LOGIN_PUB_KEY);
        String username = rsa.encryptBase64(account, KeyType.PublicKey);
        LoginUser loginUser = new LoginUser(username, password);
        // 接口调用
        ForestResponse<?> res = tmspLogClient.login(loginUser);
        ForestRequest req = res.getRequest();
        if (res.getStatusCode() == 200) {
            ITyutUser iTyutUser = updateUser(req, res, account, password);
            userMap.put(account, iTyutUser);
            log.info("[ITYUT] 用户：{} 登录成功", account);
        } else {
            throw new RuntimeException("登录失败");
        }
        // 成功返回 200, 否则返回 500 并抛出异常
        // ...
    }

    public boolean isLogin(String account) {
        return userMap.containsKey(account);
    }

    public void logout(String account) {
        ForestResponse<?> logout = tmspLogClient.logout(account);
        if (logout.getStatusCode() == 302) {
            log.info("[ITYUT] 用户：{} 退出登录成功", account);
            userMap.remove(account);
        } else {
            log.error("[ITYUT] 用户：{} 退出登录失败", account);
        }
    }

    public ITyutUser getUser(String account) {
        ITyutUser iTyutUser = userMap.get(account);
        if (iTyutUser == null) {
            throw new RuntimeException("用户未登录");
        }
        return iTyutUser;
    }

    @NotNull
    private static ITyutUser updateUser(ForestRequest request, ForestResponse response, String account, String password) {
        String host = request.getHost();
        List<ForestCookie> cookies = (List<ForestCookie>) response.getCookies();
        Map<String, Integer> cookieIndexMap = new ConcurrentHashMap<>();
        for (ForestCookie cookie : cookies) {
            cookieIndexMap.put(cookie.getName(), cookies.indexOf(cookie));
        }
        ConcurrentHashMap<String, List<ForestCookie>> cl = new ConcurrentHashMap<>();
        cl.put(host, cookies);
        ConcurrentHashMap<String, Map<String, Integer>> cm = new ConcurrentHashMap<>();
        cm.put(host, cookieIndexMap);
        return new ITyutUser(account, password, cl, cm);
    }
}
