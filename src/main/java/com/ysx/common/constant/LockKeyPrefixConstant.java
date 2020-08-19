package com.ysx.common.constant;

/**
 * @Description: 分布式锁的key
 * @Author: ysx
 */
public class LockKeyPrefixConstant {
    private static final String GLOBAL_LOCK_KEY = "baseproject_lock_";

    public static final String USER_BASE_ACTION_REGISTER = GLOBAL_LOCK_KEY+"user_base_action_register";

}
