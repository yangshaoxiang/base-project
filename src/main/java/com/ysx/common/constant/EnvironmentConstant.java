package com.ysx.common.constant;

/**
 * @Description: 系统环境常量
 * @author ysx
 */
public class EnvironmentConstant {
    /**
     *  系统当前环境 local
     */
    public static final String ENV_LOCAL = "local";

    /**
     *  系统当前环境 dev
     */
    public static final String ENV_DEV = "dev";
    /**
     *  系统当前环境 uat
     */
    public static final String ENV_UAT = "uat";
    /**
     *  系统当前环境 prod
     */
    public static final String ENV_PROD = "prod";
    /**
     *  系统当前环境 never  系统无此环境  用于关联环境的配置 表示该配置暂时不使用于任何环境
     */
    public static final String ENV_NEVER = "never";

}
