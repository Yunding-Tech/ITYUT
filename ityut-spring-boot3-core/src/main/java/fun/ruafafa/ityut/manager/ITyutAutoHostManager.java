package fun.ruafafa.ityut.manager;


import com.dtflys.forest.http.ForestResponse;
import fun.ruafafa.ityut.client.AddressClient;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Setter
public class ITyutAutoHostManager {

    @Resource
    private AddressClient addressClient;

    /**
     * 节点列表
     */
    List<String> nodes;

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
