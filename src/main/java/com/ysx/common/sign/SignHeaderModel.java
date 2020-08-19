package com.ysx.common.sign;

import cn.hutool.core.util.StrUtil;
import com.ysx.util.map.ObjectMappingMapUtil;
import com.ysx.util.map.annotation.IgnoreMapMapping;
import com.ysx.util.map.ObjectMappingMapUtil;
import com.ysx.util.map.annotation.IgnoreMapMapping;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 *  请求头中携带的 签名相关参数 请求头名称和本实体属性名称相同
 */
@Data
class SignHeaderModel {
    /**
     *  客户端时间戳 到 服务端超时时间  默认超过 3min 的请求不予验签通过 初期调试 10天内 均可通过
     */
    @IgnoreMapMapping
    private static final Long TIMEOUT = 10*24*60*60*1000L;

    /**
     *  平台  android  ios  pc  web third
     */
    private String platform;
    /**
     *  当前 默认 1.0
     */
    private String version;
    /**
     *  请求时间戳
     */
    private String timestamp;
    /**
     *  客户端签名
     */
    @IgnoreMapMapping
    private String signature;


    /**
     *  请求头中 签名必须的参数 是否有缺失
     * @return  true 请求头中 签名必须参数有缺失 或者 时间戳时间过长
     */
    boolean checkSignHeader(){
        boolean hasBlank = StrUtil.hasBlank(platform, version, timestamp, signature);
        if(!hasBlank){
            return System.currentTimeMillis() - Long.valueOf(timestamp) > SignHeaderModel.TIMEOUT;
        }
        return true;
    }

    /**
     *  将 对象属性 根据签名规则 生成 字符串签名
     *  规则: 将对象属性按照 自然排序后 生成 属性名=属性值#属性名=属性值#  这种格式的字符串
     */
    String getSignHeaderSign(){
        Map<String, Object> map = ObjectMappingMapUtil.objectToMap(this, Object.class);
        StringBuilder builder = new StringBuilder();
        if (!CollectionUtils.isEmpty(map)) {
            map.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(paramEntry -> builder.append(paramEntry.getKey()).append("=").append(paramEntry.getValue()).append('#'));
        }
        return builder.toString();
    }


}
