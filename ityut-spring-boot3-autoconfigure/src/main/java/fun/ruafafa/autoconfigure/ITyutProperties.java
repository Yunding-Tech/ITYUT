package fun.ruafafa.autoconfigure;

import fun.ruafafa.constant.TmspManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ityut")
@RequiredArgsConstructor
public class ITyutProperties {



    /**
     * 教务处学生入口网址结点
     * @see <a href="http://8.141.9.52/">反代结点ip 无需连接校园网</a>
     * @see <a href="http://jwc.ruafafa.fun">反代结点 无需连接校园网</a>
     * @see <a href="http://jwc.ruafafa.fun">官方结点1 自行连接校园网</a>
     * @see <a href="http://jwc.ruafafa.fun">官方结点2 自行连接校园网</a>
     */
    private final TmspManager teachingManagementTmspManager;
}
