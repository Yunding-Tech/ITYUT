package fun.ruafafa.ityut.server.impl;


import fun.ruafafa.ityut.client.TmspClient;
import fun.ruafafa.ityut.server.ITyutService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * ITyut 服务调用
 */
@Component
public class ITyutServerImpl implements ITyutService {
    @Resource
    private TmspClient tmspClient;

    @Override
    public String getAcademicYearTerm() {
        return null;
    }

}
