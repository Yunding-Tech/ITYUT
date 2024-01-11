package fun.ruafafa.ityut.interceptor;

import com.dtflys.forest.callback.AddressSource;
import com.dtflys.forest.http.ForestAddress;
import com.dtflys.forest.http.ForestRequest;
import fun.ruafafa.ityut.manager.AutoLoginManager;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@DependsOn({"autoLoginManager"})
@Component
public class TmspAddressSource implements AddressSource {

    @Resource
    private AutoLoginManager autoLoginManager;

    @Override
    public ForestAddress getAddress(ForestRequest forestRequest) {
        String currAddress = autoLoginManager.getUseableAddress();
        return new ForestAddress(currAddress, 80);
    }

}
