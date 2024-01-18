package fun.ruafafa.ityut.manager;


import com.dtflys.forest.http.ForestResponse;
import fun.ruafafa.ityut.client.AddressClient;
import fun.ruafafa.ityut.constant.LoginConstant;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("iTyutAutoHostManager")
@Data
public class ITyutAutoHostManager {

    @Resource
    private AddressClient addressClient;

    /**
     * 节点列表
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

    public void addCustomNode(String node) {
        nodes.add(node);
    }

}
