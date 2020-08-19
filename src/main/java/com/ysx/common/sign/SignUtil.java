package com.ysx.common.sign;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.auth0.jwt.internal.org.apache.commons.lang3.ArrayUtils;
import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 验签工具类
 * @Author: ysx
 */
public class SignUtil {

    /**
     * 根据规则生成签名
     * 规则如下:
     * 总体: sha256(请求头字符串+body体json字符串+普通键值对参数字符串+restful 类型连接上参数字符串)
     *
     *  *   请求头必须携带
     *  *   platform (不同端可选值 android  ios  pc  web third)
     *  *   version (当前默认传 1.0)
     *  *   timestamp (时间戳毫秒值)
     *  *   signature (客户端签名)
     *
     *
     *  请求头字符串生成规则:
     *   必传的 4 个 请求头,signature 不参与生成规则，其他 3 个请求头 首先按照 请求头名称自然排序，之后按照 名称=值# 的方式生成字符串
     *   生成的字符串参考结果: platform=web#timestamp=1586421539000#version=1.0#
     *
     *  body 字符串生成规则
     *  建议将 body 的 json 串 压缩后传递，尽量不要传递漂亮格式(包含大量换行空格）,这样一方面加密不易出错，另一方面节省流量
     *  压缩后 加上 #  号
     *  生成的字符串参考结果: {"name":"zhangsan","age":22}#
     *
     *  普通的键值对参数字符串生成规则
     *  首先按照参数名称自然排序
     *  对于 普通的键值对参数 规则为 param=value#
     *  对于 checkbox 键值对参数 规则为 param=value1,value2#  对于值 value1,value2 按照自然排序
     *  生成的字符串参考结果: param3=3,33#param4=4#
     *
     *  restful 类型 连接上的参数
     *  连接上的参数值 按照自然排序 用 , 号隔开，末尾不需要加 # 号
     *  生成的字符串参考结果: 1,2
     *
     *  按照参考结果生成的完整字符串为:
     *  platform=web#timestamp=1586421539000#version=1.0#{"name":"zhangsan","age":22}#param3=3,33#param4=4#1,2
     *
     *  执行
     *  sha256(platform=web#timestamp=1586421539000#version=1.0#{"name":"zhangsan","age":22}#param3=3,33#param4=4#1,2)
     *  加密后生成的 signature 为
     *  9b51665b4ea30173334d8087efcb04a826c21ea588f98804f78809497a822de1
     *
     *  将生成的 signature 添加到 header 中
     *
     * @param signHeaderModel 签名相关的请求头
     * @param body 放在 body 体中的 json 串
     * @param params 普通的键值对参数
     * @param paths restful 类型 连接上参数 值是string[]的原因是 对应checkbox参数值为数组
     * @return 根据规则生成的签名
     */
     static String sign(SignHeaderModel signHeaderModel,String body, Map<String, String[]> params, String[] paths) {

        StringBuilder sb = new StringBuilder();
        // 处理请求头
         String signHeaderSign = signHeaderModel.getSignHeaderSign();
         sb.append(signHeaderSign);

         // 处理 body
         if (StringUtils.isNotBlank(body)) {
            sb.append(body).append('#');
        }

        // 处理 普通键值对参数
        if (!CollectionUtils.isEmpty(params)) {
            params.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(paramEntry -> {
                        String paramValue = String.join(",", Arrays.stream(paramEntry.getValue()).sorted().toArray(String[]::new));
                        sb.append(paramEntry.getKey()).append("=").append(paramValue).append('#');
                    });
        }

        // 处理 restful 形式 连接上参数
        if (ArrayUtils.isNotEmpty(paths)) {
            String pathValues = String.join(",", Arrays.stream(paths).sorted().toArray(String[]::new));
            sb.append(pathValues);
        }
         String string = sb.toString();
        // System.out.println("加密前:"+string);
         // return SecureUtil.hmac(HmacAlgorithm.HmacSHA256,string).;
        return SecureUtil.sha256(string);
    }

    public static void main(String[] args) {
        SignHeaderModel signHeaderModel = new SignHeaderModel();
        signHeaderModel.setPlatform("web");
        signHeaderModel.setVersion("1.0");
        signHeaderModel.setTimestamp("1589013913144");
        signHeaderModel.setSignature("fa51173b7dd2046ba16ad33656590217699eb7b8ea89bf722b8258a83f26b4e1");

        String body = "{\"name\":\"zhangsan\",\"age\":22}";
        Map<String, String[]> params = new HashMap<>(16);
        params.put("param4", new String[]{"4"});
        params.put("array", new String[]{"3,33"});

        String[] paths = new String[]{"2", "1"};

        System.out.println(sign(signHeaderModel,body,params,paths));

    }

}