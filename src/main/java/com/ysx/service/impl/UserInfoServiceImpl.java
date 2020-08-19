package com.ysx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ysx.entity.AccountCoin;
import com.ysx.entity.UserInfo;
import com.ysx.mapper.UserInfoMapper;
import com.ysx.service.AccountCoinService;
import com.ysx.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author ysx
 * @since 2020-04-26
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private AccountCoinService accountCoinService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(UserInfo userInfo ){
        // 注册信息
        userInfoMapper.insert(userInfo);
        // 给用户开账户
        AccountCoin accountCoin = new AccountCoin();
        accountCoin.setUserId(userInfo.getId());
        accountCoin.setCoinUsable(100);
        accountCoin.setCoinFreeze(0);
        accountCoin.setState(1);
        accountCoinService.save(accountCoin);
    }

}
