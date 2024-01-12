package fun.ruafafa.ityut.manager;


import com.dtflys.forest.http.ForestResponse;
import fun.ruafafa.ityut.client.AddressClient;
import fun.ruafafa.ityut.constant.LoginConstant;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AutoHostManager {

    @Resource
    private AddressClient addressClient;

    /**
     * 默认节点列表
     */
    List<String> nodes = List.of(LoginConstant.IP_RUAFAFA, LoginConstant.DOMAIN_RUAFAFA,
            LoginConstant.DOMAIN_AUTHORITY_1, LoginConstant.DOMAIN_AUTHORITY_2);

    /**
     * 自动选择节点
     * @return address
     */
    public String autoChooseAddress() {
        for (String node : nodes) {
            ForestResponse<?> response = addressClient.checkAddress(node);
            if (response.getStatusCode() == 200) {
                return node;
            }
        }
        return null;
    }

}
