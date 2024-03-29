import org.apache.ibatis.io.Resources;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成器测试类
 * @version V1.0
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/10/15 14:53
 */
public class GeneratorTest {
    public static void main(String[] args) throws Exception{
        List<String> warnings = new ArrayList<>();
        // 是否覆盖以前生成的重复代码
        boolean overwrite = true;
        // 配置文件的资源路径，会去classpath下找。maven项目中，就是resource下
        InputStream in = Resources.getResourceAsStream("generatorConfig.xml");
        // 配置解析器
        ConfigurationParser cp = new ConfigurationParser(warnings);
        // 加载配置文件，并解析
        Configuration config = cp.parseConfiguration(in);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        // 获取MBG对象
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        // 生成代码
        myBatisGenerator.generate(null);

    }
}
