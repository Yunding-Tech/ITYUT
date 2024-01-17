package fun.ruafafa.ityut.interceptor;

import com.dtflys.forest.exceptions.ForestRuntimeException;
import com.dtflys.forest.http.ForestCookie;
import com.dtflys.forest.http.ForestCookies;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.Interceptor;
import fun.ruafafa.ityut.manager.ITyutUserManager;
import fun.ruafafa.ityut.manager.entity.ITyutUser;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理Cookie的拦截器
 */
@Component
@DependsOn("ITyutUserManager")
public class ITyutCookieInterceptor implements Interceptor {

    @Resource
    private ITyutUserManager iTyutUserManager;

    /**
     * 在请求响应成功后，需要保存Cookie时调用该方法
     *
     * @param request Forest请求对象
     * @param cookies Cookie集合，通过响应返回的Cookie都从该集合获取
     */
    @Override
    public void onSaveCookie(ForestRequest request, ForestCookies cookies) {
        String account = (String) request.getAttachment("account");
        ITyutUser user = iTyutUserManager.getUser(account);
        Map<String, List<ForestCookie>> cookieCache = user.getCookieCache();
        Map<String, Map<String, Integer>> cookieIndexCache = user.getCookieIndexCache();
        // 获取请求URI的主机名
        String host = request.getURI().getHost();
        List<ForestCookie> caciList = cookieCache.getOrDefault(host, new ArrayList<>());
        Map<String, Integer> cciMap = cookieIndexCache.getOrDefault(host, new ConcurrentHashMap<>());
        // 将从服务端获得的Cookie列表放入缓存，主机名作为Key
        List<ForestCookie> setCookies = cookies.getCookies(host);
        for (ForestCookie setCookie : setCookies) {
            String name = setCookie.getName();
            if (!cciMap.containsKey(name)) {
                // 不存在相同名称的Cookie，添加到缓存
                caciList.add(setCookie);
                // 更新索引
                cciMap.put(name, cciMap.getOrDefault(name, caciList.size()));
            } else {
                // 已存在相同名称的Cookie，更新值
                caciList.get(cciMap.get(name)).setValue(setCookie.getValue());
            }
        }
        cookieCache.put(host, caciList);
        cookieIndexCache.put(host, cciMap);
    }

    /**
     * 在发送请求前，需要加载Cookie时调用该方法
     *
     * @param request Forest请求对象
     * @param cookies Cookie集合, 需要通过请求发送的Cookie都添加到该集合
     */
    @Override
    public void onLoadCookie(ForestRequest request, ForestCookies cookies) {
        String account = (String) request.getAttachment("account");
        ITyutUser user = iTyutUserManager.getUser(account);
        Map<String, List<ForestCookie>> cookieCache = user.getCookieCache();
        // 获取请求URI的主机名
        String host = request.getURI().getHost();
        // 从缓存中获取之前获得的Cookie列表，主机名作为Key
        List<ForestCookie> cookieList = cookieCache.get(host);
        // >> START 处理节点中途更换的情况，自动重新登录 <<
        if (cookieList == null) {
            iTyutUserManager.login(account, user.getPassword());
            cookieList = cookieCache.get(host);
        }
        // >> END <<
        // 将缓存中的Cookie列表添加到请求Cookie列表中，准备发送到服务端
        // 默认情况下，只有符合条件 (和请求同域名、同URL路径、未过期) 的 Cookie 才能被添加到请求中
        cookies.addAllCookies(cookieList);
    }

    @Override
    public void onError(ForestRuntimeException ex, ForestRequest request, ForestResponse response) {
        // ... ...
    }

    @Override
    public void onSuccess(Object data, ForestRequest request, ForestResponse response) {
        // ... ...
    }
}
