package fun.ruafafa.ityut.interceptor;

import com.dtflys.forest.callback.AddressSource;
import com.dtflys.forest.http.ForestAddress;
import com.dtflys.forest.http.ForestRequest;
import fun.ruafafa.ityut.manager.ITyutAutoHostManager;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@DependsOn({"iTyutAutoHostManager"})
public class TmspAddressSource implements AddressSource {

    @Resource
    private ITyutAutoHostManager ITyutAutoHostManager;

    @Override
    public ForestAddress getAddress(ForestRequest req) {
        String address = ITyutAutoHostManager.autoChooseAddress();
        if (address == null) {
            throw new RuntimeException("无可用节点!!!");
        }
        return new ForestAddress(address, 80);
    }

}
