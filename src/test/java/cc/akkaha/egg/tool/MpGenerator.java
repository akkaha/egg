package cc.akkaha.egg.tool;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MpGenerator {

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("src/main/java/");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setAuthor("Auto");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("rd");
        dsc.setPassword("rd");
        dsc.setUrl("jdbc:mysql://localhost:3306/egg?characterEncoding=utf-8");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setTablePrefix(new String[]{""});
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setEntityColumnConstant(true);
        strategy.setRestControllerStyle(true);
        // strategy.setInclude(new String[]{"p_jira_opt_log"});
        // strategy.setExclude(new String[]{"p_authority","p_platform","p_platform_group",
        // "p_promise","p_role","p_user"});
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cc.akkaha");
        pc.setModuleName("egg");
        pc.setMapper("db.client");
        pc.setEntity("db.model");
        //pc.setService("service");
        //pc.setServiceImpl("service.impl");
        pc.setXml("db.mybatis.client");
        //pc.setController("controller");

        mpg.setPackageInfo(pc);

        // 注入自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                this.setMap(map);
            }
        };

        // 自定义生成
        List<FileOutConfig> focList = new ArrayList<>();

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 调整 xml 生成目录
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return getRootPath() + "/src/main/resources/Mapper/" + tableInfo.getEntityName()
                        + "Mapper.xml";
            }
        });

//        // 调整 Service 生成
//        focList.add(new FileOutConfig("/templates/service.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return getRootPath() + "/src/main/java/com/guazi/qa/casemgr/service/" +
//                        tableInfo.getEntityName().substring(1) + "Service.java";
//            }
//        });
//        // 调整 ServiceImpl 生成
//        focList.add(new FileOutConfig("/templates/serviceImpl.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return getRootPath() + "/src/main/java/com/guazi/qa/casemgr/service/impl/" +
//                        tableInfo.getEntityName().substring(1) + "ServiceImpl.java";
//            }
//        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 关闭默认生成，应用自定义生成
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        tc.setController(null);
        //tc.setService(null);
        //tc.setServiceImpl(null);
        mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

    }


    /**
     * 获取项目根路径
     *
     * @return 项目路径
     */
    private static String getRootPath() {
        File directory = new File("");// 参数为空
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courseFile;
    }
}
