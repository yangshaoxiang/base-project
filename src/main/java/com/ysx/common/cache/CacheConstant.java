package com.ysx.common.cache;

/**
 *  缓存 key 相关常量
 */
public class CacheConstant {
    
    private CacheConstant(){}

    /**
     *  单条查询 缓存 key 前缀
     */
    public static final String SINGLE_CATCH_KEY = "single_";

    /**
     *  动态条件列表查询 缓存 key 前缀
     */
    public static final String LIST_CATCH_KEY = "list_";

    // ----------------- 以下为各个模块缓存 key 名称 ---------------
    /**
     *  问题
     */
    public static final String CACHE_MODULE_QUESTION = "question_";

    public static final String CACHE_MODULE_ANSWER = "answer_";


    public static final String CACHE_MODULE_CONFIG = "config_module_";

    public static final String CACHE_BANNER_CONFIG = "config_banner_";







}
