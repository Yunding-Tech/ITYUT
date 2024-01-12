package fun.ruafafa.ityut.interceptor;

import com.dtflys.forest.callback.AddressSource;
import com.dtflys.forest.http.ForestAddress;
import com.dtflys.forest.http.ForestBody;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestRequestBody;
import com.dtflys.forest.http.body.ObjectRequestBody;
import fun.ruafafa.ityut.dto.LoginUser;
import fun.ruafafa.ityut.manager.AutoHostManager;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

@DependsOn({"autoHostManager"})
@Component
public class TmspAddressSource implements AddressSource {

    @Resource
    private AutoHostManager autoHostManager;

    @Override
    public ForestAddress getAddress(ForestRequest req) {
        String address = autoHostManager.autoChooseAddress();
        if (address == null) {
            throw new RuntimeException("无可用节点!!!");
        }
        System.out.println("当前节点：" + address);
        return new ForestAddress(address, 80);
    }

}
