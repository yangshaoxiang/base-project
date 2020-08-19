package com.ysx.common.cache;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @Description: Spring boot catch key 生成策略
 * @author ysx
 */
public class SpringCacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object targetClass, Method method, Object... params) {
        HashMap<String, Object> map = new HashMap<>(16);

        Class<?> targetClassClass = targetClass.getClass();

        // 类地址
        map.put("class",targetClassClass.toGenericString());

        // 方法名称
        map.put("methodName",method.getName());

        // 包名称
        map.put("package",targetClassClass.getPackage());
        // 参数列表
        for (int i = 0; i < params.length; i++) {
            map.put(String.valueOf(i),params[i]);
        }
        // 转为JSON字符串
        String jsonStr = JSONUtil.toJsonStr(map);
        // 做SHA256 Hash计算，得到一个SHA256摘要作为Key
        return SecureUtil.sha256(jsonStr);
    }
}
