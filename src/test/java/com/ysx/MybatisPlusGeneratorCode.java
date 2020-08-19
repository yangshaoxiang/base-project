package com.ysx;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * 基础代码自动生成器 一般只需要修改下面静态成员变量即可，如果包路径发生变化，main 方法中包路径注意修改
 */
public class MybatisPlusGeneratorCode {

    /**
     * 生成代码的作者
     */
    private static final String AUTHOR = "ysx";
    /**
     * 要逆向生成基础代码的表
     */
    private static final String[] TABLE_NAMES = {"config_user_module"};
    /**
     *  数据库信息
     */
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/base_dev?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PWD = "123456";


    public static void main(String[] args) {
        // 需要构建一个 代码自动生成器 对象
        AutoGenerator mpg = new AutoGenerator();
        // 配置策略
        // 1、全局配置
        GlobalConfig gc = new GlobalConfig();

        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath+"/src/main/java");
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setFileOverride(false); // 是否覆盖
        gc.setServiceName("%sService"); // 去Service的I前缀
        gc.setIdType(IdType.AUTO);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        mpg.setGlobalConfig(gc);

        //2、设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(DB_URL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(DB_USER);
        dsc.setPassword(DB_PWD);
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);
        //3、配置 生成的类所在的包 不配 默认
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName("xxx");
        pc.setParent("com.ysx");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        //设置不同类文件生成的路径
        HashMap<String, String> pathMap = new HashMap<>();
        pathMap.put(ConstVal.XML_PATH,projectPath+"/src/main/resources/mapper/");
        pathMap.put(ConstVal.CONTROLLER_PATH,projectPath+"/src/main/java/com/ysx/controller/");
        pathMap.put(ConstVal.MAPPER_PATH,projectPath+"/src/main/java/com/ysx/mapper/");
        pathMap.put(ConstVal.ENTITY_PATH,projectPath+"/src/main/java/com/ysx/entity/");
        pathMap.put(ConstVal.SERVICE_PATH,projectPath+"/src/main/java/com/ysx/service/");
        pathMap.put(ConstVal.SERVICE_IMPL_PATH,projectPath+"/src/main/java/com/ysx/service/impl/");
        pc.setPathInfo(pathMap);
        mpg.setPackageInfo(pc);
        //4、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(TABLE_NAMES); // 设置要映射的表名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true); // 自动lombok；
        // strategy.setTablePrefix("tbl_"); // 表前缀
        strategy.setLogicDeleteFieldName("is_deleted");
        // 自动填充配置
        TableFill gmtCreate = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time", FieldFill.INSERT);
        //TableFill gmtModified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(updateTime);
        strategy.setTableFillList(tableFills);
        // 乐观锁
        //strategy.setVersionFieldName("version");
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.execute();
  }
}