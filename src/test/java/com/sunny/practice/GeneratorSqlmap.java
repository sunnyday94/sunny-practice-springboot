package com.sunny.practice; /**
 * FileName: TestO
 * Author:   sunny
 * Date:     2018/9/5 11:50
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

/**
 * @description
 * @author sunny
 * @create 2018/9/5
 * @since 1.0.0
 */

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GeneratorSqlmap {
    public static void main(String[] args){
        try {
            // 信息缓存
            List<String> warnings = new ArrayList<>();
            // 覆盖已有的重名文件
            //boolean overwrite = true;
            // 准备 配置文件
            File configFile = new File("E:\\IDEAWorkSpace\\sunny-practice-springboot\\src\\main\\resources\\generatorConfig.xml");
            // 1.创建 配置解析器
            ConfigurationParser parser = new ConfigurationParser(warnings);
            // 2.获取 配置信息
            Configuration config = parser.parseConfiguration(configFile);
            // 3.创建 默认命令解释调回器
            DefaultShellCallback callback = new DefaultShellCallback(true);
            // 4.创建 mybatis的生成器
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            // 5.执行，关闭生成器
            myBatisGenerator.generate(null);
            System.out.println("生成成功!");
        } catch (Exception e) {
            System.err.println("生成失败!");
            e.printStackTrace();
        }
    }

}
