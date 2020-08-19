package com.ysx.service;

import com.ysx.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author ysx
 * @since 2020-04-26
 */
public interface UserInfoService extends IService<UserInfo> {


    /**
     *  注册
     * @param userInfo 用户信息
     */
    void register(UserInfo userInfo);

}
