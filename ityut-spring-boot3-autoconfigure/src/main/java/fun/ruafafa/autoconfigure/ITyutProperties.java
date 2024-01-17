package fun.ruafafa.autoconfigure;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

@ConfigurationProperties(prefix = "ityut")
@RequiredArgsConstructor
@Data
public class ITyutProperties {


    /**
     * 教务处学生入口网址结点
     * @see <a href="http://8.141.9.52/">反代结点ip 无需连接校园网</a>
     * @see <a href="http://jwc.ruafafa.fun">反代结点 无需连接校园网</a>
     * @see <a href="http://jwc.ruafafa.fun">官方结点1 自行连接校园网</a>
     * @see <a href="http://jwc.ruafafa.fun">官方结点2 自行连接校园网</a>
     */
    private List<String> customNodes;
}
