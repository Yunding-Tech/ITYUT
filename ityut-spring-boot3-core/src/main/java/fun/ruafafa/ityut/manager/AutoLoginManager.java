package fun.ruafafa.ityut.manager;


import com.dtflys.forest.http.ForestResponse;
import fun.ruafafa.ityut.client.PreLoadClient;
import fun.ruafafa.ityut.client.TmspClient;
import fun.ruafafa.ityut.constant.LoginConstant;
import fun.ruafafa.ityut.exception.NoAvailableNodeException;
import fun.ruafafa.ityut.dto.ITyutLoginUser;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component("autoLoginManager")
public class AutoLoginManager {
    /**
     * 当前可用节点
     */
    private String currAddress;

    /**
     * 用户类
     */
    private final ITyutLoginUser iTyutLoginUser;

    /**
     * 预加载指定节点请求客户端
     */
    private final PreLoadClient preLoadClient;

    /**
     * 自动节点选择请求客户端
     */
    private final TmspClient tmspClient;

    /**
     * 默认节点列表
     */
    List<String> nodes = List.of(LoginConstant.IP_RUAFAFA, LoginConstant.DOMAIN_RUAFAFA,
            LoginConstant.DOMAIN_AUTHORITY_1, LoginConstant.DOMAIN_AUTHORITY_2);

    /**
     * 预加载可用节点，激活后续自动节点选择
     */
    @PostConstruct
    public void initAddress() {
        autoChooseAddress();
    }

    /**
     * 获取可用节点
     * @return 可用节点
     */
    public String getUseableAddress() {
        if (isNodeAvailable(currAddress))
            return currAddress;
        else
            return autoChooseAddress();
    }

    /**
     * 自动选择一个可用节点
     * @return
     */
    public String autoChooseAddress() {
        for (String node : nodes) {
            if (isNodeAvailable(node)) {
                this.currAddress = node;
                // 已登录，更换节点尝试自动登录
                if (iTyutLoginUser.isLogin) {
                    // TODO: log
                    // 获取 session 并使用已设置的账户密码（成功登陆过） 登录
                    tmspClient.getResponse();
                    tmspClient.login(iTyutLoginUser);
                }
                return currAddress;
            }
        }
        throw new NoAvailableNodeException("无可用结点");
    }

    /**
     * 测试节点是否有效
     * @param address
     * @return
     */
    public boolean isNodeAvailable(String address) {
        ForestResponse<?> resp = preLoadClient.getResponse(address);
        int statusCode = resp.getStatusCode();
        if (statusCode != 200) return false;
        return true;
    }
}
