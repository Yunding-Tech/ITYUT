package fun.ruafafa.autoconfigure;

import com.dtflys.forest.springboot.annotation.ForestScan;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@ForestScan(basePackages = "fun.ruafafa.client")
@ConditionalOnWebApplication
@EnableConfigurationProperties(ITyutProperties.class)
@ConditionalOnProperty(prefix = "ityut", value = "enable", matchIfMissing = true)
public class ITyutConfiguration {

    // 注入配置文件
    private final ITyutProperties properties;

    // 装配 Bean


}
