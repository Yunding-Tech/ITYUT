package fun.ruafafa.autoconfigure;

import com.dtflys.forest.springboot.annotation.ForestScan;
import fun.ruafafa.ityut.ITyutUtil;
import fun.ruafafa.ityut.constant.LoginConstant;
import fun.ruafafa.ityut.manager.ITyutAutoHostManager;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

import java.util.List;

@Configuration
@ForestScan(basePackages = "fun.ruafafa.ityut.client")
@EnableConfigurationProperties(ITyutProperties.class)
@ConditionalOnProperty(prefix = "ityut", value = "enable", matchIfMissing = true)
@ComponentScan(basePackages = "fun.ruafafa.ityut")
@Import({ITyutUtil.class})
public class ITyutAutoConfiguration {

    // 注入配置文件
    @Resource
    private ITyutProperties properties;

    // 装配 Bean
    /**
     * 自动节点选择管理器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ITyutAutoHostManager iTyutAutoHostManager() {
        List<String> customNodes = properties.getCustomNodes();
        ITyutAutoHostManager ma = new ITyutAutoHostManager();
        if (customNodes != null) {
            // 自定义优先
            List<String> nodes = ma.getNodes();
            customNodes.addAll(nodes);
            ma.setNodes(customNodes);
            return ma;
        }
        return ma;
    }

}
